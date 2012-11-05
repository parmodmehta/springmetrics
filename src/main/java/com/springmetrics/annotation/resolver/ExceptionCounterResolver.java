package com.springmetrics.annotation.resolver;

import java.lang.reflect.Method;

import com.springmetrics.annotation.ExceptionCounter;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.MetricName;

/**
 * @author pmehta
 * 
 */
public class ExceptionCounterResolver extends BaseMetricsResolver {

	private com.yammer.metrics.core.Counter exceptionCounter;

	public ExceptionCounterResolver(final ExceptionCounter exceptionCounter, final Throwable e, final Method m) {
		Class<?> klass = m.getDeclaringClass();
		MetricName name = new MetricName(getGroup(exceptionCounter.group(), klass), getType(e.getClass()
				.getCanonicalName(), klass), getName(exceptionCounter.name(), m));
		this.exceptionCounter = Metrics.newCounter(name);
	}

	public void inc() {
		this.exceptionCounter.inc();
	}

}
