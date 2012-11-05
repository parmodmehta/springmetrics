package com.springmetrics.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.interceptor.CacheAspectSupport.Invoker;
import org.springframework.core.annotation.AnnotationUtils;

import com.springmetrics.annotation.Timed;
import com.springmetrics.annotation.resolver.TimedMetricsResolver;
import com.yammer.metrics.core.TimerContext;

/**
 * @author pmehta
 *
 */
public aspect AnnotationTimedAspect {

	public pointcut executionOfTimedAnnotatedMethod() : execution(@Timed * *(..));
	
	@SuppressAjWarnings("adviceDidNotMatch")
	Object around() : counterAnnotatedMethodExecution() {
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Timed annot = AnnotationUtils.findAnnotation(method, Timed.class);
		final TimerContext context = new TimedMetricsResolver(annot, method).getTimerContext();
		Invoker aspectJInvoker = new Invoker() {
			public Object invoke() {
				try {
					return proceed();
				} finally {
					context.stop();
				}
			}
		};
		return aspectJInvoker.invoke();
	}

	public pointcut counterAnnotatedMethodExecution() : executionOfTimedAnnotatedMethod();

}
