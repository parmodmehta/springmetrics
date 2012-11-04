package com.springmetrics.interceptor;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInvocation;

import com.springmetrics.annotation.Timed;
import com.springmetrics.annotation.resolver.TimerMetricsResolver;
import com.yammer.metrics.core.TimerContext;

/**
 * @author pmehta
 *
 */
public class TimedInterceptor extends AbstractMethodInterceptor {

	public TimedInterceptor(Object bean) {
		super(bean);
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Annotation annotation = findAnnotation(invocation.getMethod(), Timed.class);
		if (annotation!=null) {
			TimerContext context = new TimerMetricsResolver((Timed)annotation, invocation.getMethod()).getTimerContext();
			try {
				return invocation.proceed();
			} finally {
				context.stop();
			}
		}
		return invocation.proceed();
	}
}
