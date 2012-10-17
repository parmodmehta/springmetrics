package com.springmetrics.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.springmetrics.annotation.Counter;
import com.springmetrics.annotation.resolver.CounterResolver;

/**
 * 
 * @author pmehta
 *
 */
public class CounterDynamicProxy extends AbstractDynamicProxy {

	public CounterDynamicProxy(final Object targetObject) {
		super(targetObject);
	}
	
	protected Annotation getAnnotation(final Method method) {
		return (Counter)findAnnotation(Counter.class, method);
	}

	@Override
	protected Object doInvoke(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable {
		CounterResolver resolver = new CounterResolver((Counter)annotation, method);
		resolver.inc();
		try {
			return method.invoke(targetObject, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}
