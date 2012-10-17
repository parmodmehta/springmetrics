package com.springmetrics.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.annotation.resolver.ExceptionCounterResolver;

/**
 * @author pmehta
 *
 */
public aspect AnnotationExceptionCounterAspect {

	public pointcut executionCounterAnnotated() : execution(@ExceptionCounter * *(..));

	@SuppressAjWarnings("adviceDidNotMatch")
	after() throwing(Exception e) : exceptionCounterMethodExecution() {
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		final Method method = methodSignature.getMethod();
		final ExceptionCounter annot = AnnotationUtils.findAnnotation(method, ExceptionCounter.class);
		for (Class<? extends Exception> ex : annot.exceptions()) {
			if (ex.isInstance(e)) {
				ExceptionCounterResolver resolver = new ExceptionCounterResolver(annot, e, method);
				resolver.inc();
			}
		}
	}
	
	public pointcut exceptionCounterMethodExecution() : executionCounterAnnotated();

}
