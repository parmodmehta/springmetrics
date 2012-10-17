package com.springmetrics.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import com.springmetrics.annotation.Counter;
import com.springmetrics.annotation.resolver.CounterResolver;

/**
 * @author pmehta
 *
 */
public aspect AnnotationCounterAspect {

	public pointcut executionOfCounterAnnotatedMethod() : execution(@Counter * *(..));
	
	@SuppressAjWarnings("adviceDidNotMatch")
	before() : counterAnnotatedMethodExecution() {
		MethodSignature methodSignature = (MethodSignature) thisJoinPointStaticPart.getSignature();
		Method method = methodSignature.getMethod();
		Counter annot = AnnotationUtils.findAnnotation(method, Counter.class);
		CounterResolver resolver = new CounterResolver(annot, method);
		resolver.inc();
	}

	public pointcut counterAnnotatedMethodExecution() : executionOfCounterAnnotatedMethod();
}
