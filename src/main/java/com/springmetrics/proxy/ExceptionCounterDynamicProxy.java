package com.springmetrics.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.annotation.resolver.ExceptionCounterResolver;

/**
 * @author pmehta
 *
 */
public class ExceptionCounterDynamicProxy extends AbstractDynamicProxy {

	public ExceptionCounterDynamicProxy(final Object targetObject) {
		super(targetObject);
	}
	
	protected Annotation getAnnotation(final Method method) {
		return (ExceptionCounter)findAnnotation(ExceptionCounter.class, method);
	}

	@Override
	protected Object doInvoke(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable {
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
