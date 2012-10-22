package com.springmetrics.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.aop.support.AopUtils;

import com.springmetrics.annotation.Counter;
import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.annotation.Timed;
import com.springmetrics.annotation.resolver.CounterResolver;
import com.springmetrics.annotation.resolver.ExceptionCounterResolver;
import com.springmetrics.annotation.resolver.TimerMetricsResolver;
import com.yammer.metrics.core.TimerContext;

/**
 * @author pmehta
 *
 */
public class SpringMetricsDynamicProxy implements InvocationHandler {

	@SuppressWarnings("unchecked")
	private static final List<Class<? extends Annotation>> VALID_ANNOTATIONS = Arrays.asList(Timed.class, Counter.class, ExceptionCounter.class);
	protected Object targetObject;
	protected Method[] methods;
	
	public SpringMetricsDynamicProxy(final Object targetObject) {
		this.targetObject = targetObject;
		this.methods = AopUtils.getTargetClass(targetObject).getDeclaredMethods();
	}

	private Annotation findAnnotation(final Method method) {
		Annotation found = null;
		for (Method m : methods) {
			if (Utils.equals(m,method)) {
				for (Class<? extends Annotation> annotation : VALID_ANNOTATIONS) {
					found = Utils.findAnnotation(targetObject, m, annotation);
					if (found!=null) 
						return found;
				}
			}
		}
		return null;
	}
	
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		Annotation annotation = findAnnotation(method);
		
		if (annotation!=null) {
			if (annotation.annotationType().equals(Counter.class))
				executeCounterAnnotation(proxy,method,args,annotation);
			
			if (annotation.annotationType().equals(Timed.class)) 
				executeTimedAnnotation(proxy,method,args,annotation);
			
			if (annotation.annotationType().equals(ExceptionCounter.class))
				executeExceptionCounterAnnotation(proxy,method,args,annotation);
		}
		
		return method.invoke(targetObject, args);
	}
	
	
	private Object executeCounterAnnotation(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable {
		CounterResolver resolver = new CounterResolver((Counter)annotation, method);
		resolver.inc();
		try {
			return method.invoke(targetObject, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
	
	private Object executeTimedAnnotation(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable {
		TimerContext context = new TimerMetricsResolver((Timed)annotation, method).getTimerContext();
		try {
			return method.invoke(targetObject, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} finally {
			context.stop();
		}
	}

	private Object executeExceptionCounterAnnotation(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable {
		try {
			return method.invoke(targetObject, args);
		} catch(Exception e) {
			for (Class<? extends Exception> ex : ((ExceptionCounter)annotation).exceptions()) {
				if (ex.isInstance(e.getCause())) { 
					ExceptionCounterResolver resolver = new ExceptionCounterResolver((ExceptionCounter)annotation, e, method);
					resolver.inc();
				}
			}
			throw e.getCause();
		} 
	}
	
}
