package com.springmetrics.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.springmetrics.annotation.resolver.TimerMetricsResolver;
import com.yammer.metrics.annotation.Timed;
import com.yammer.metrics.core.TimerContext;

/**
 * @author pmehta
 *
 */
public class TimedDynamicProxy extends AbstractDynamicProxy {

	public TimedDynamicProxy(final Object targetObject) {
		super(targetObject);
	}
	
	protected Annotation getAnnotation(final Method method) {
		return (Timed)findAnnotation(Timed.class, method);
	}
	
	protected Object doInvoke(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable {
		TimerContext context = new TimerMetricsResolver((Timed)annotation, method).getTimerContext();
		try {
			return method.invoke(targetObject, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} finally {
			context.stop();
		}
	}

}
