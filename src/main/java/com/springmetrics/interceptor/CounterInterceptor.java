package com.springmetrics.interceptor;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInvocation;

import com.springmetrics.annotation.Counter;
import com.springmetrics.annotation.resolver.CounterResolver;

/**
 * @author pmehta
 *
 */
public class CounterInterceptor extends AbstractMethodInterceptor {

	public CounterInterceptor(Object bean) {
		super(bean);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Annotation annotation = findAnnotation(invocation.getMethod(), Counter.class);
		if (annotation!=null) {
			CounterResolver resolver = new CounterResolver((Counter)annotation, invocation.getMethod());
			resolver.inc();
		}
		return invocation.proceed();
	}
}
