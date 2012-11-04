package com.springmetrics.interceptor;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInvocation;

import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.annotation.resolver.ExceptionCounterResolver;

/**
 * @author pmehta
 *
 */
public class ExceptionCounterInterceptor extends AbstractMethodInterceptor {

	public ExceptionCounterInterceptor(Object bean) {
		super(bean);
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Annotation annotation = findAnnotation(invocation.getMethod(), ExceptionCounter.class);
		if (annotation!=null) {
			try {
				return invocation.proceed();
			} catch(Throwable e) {
				for (Class<? extends Exception> ex : ((ExceptionCounter)annotation).exceptions()) {
					if (ex.isAssignableFrom(e.getClass())) { 
						ExceptionCounterResolver resolver = new ExceptionCounterResolver((ExceptionCounter)annotation, e, invocation.getMethod());
						resolver.inc();
					}
				}
				throw e;
			} 
		}
		return invocation.proceed();
	}
}
