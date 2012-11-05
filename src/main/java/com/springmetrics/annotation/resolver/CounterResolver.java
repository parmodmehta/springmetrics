package com.springmetrics.annotation.resolver;

import java.lang.reflect.Method;

import com.springmetrics.annotation.Counter;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.MetricName;

/**
 * @author pmehta
 * 
 */
public class CounterResolver extends BaseMetricsResolver {

	private com.yammer.metrics.core.Counter counter;

	public CounterResolver(final Counter c, final Method m) {
		Class<?> klass = m.getDeclaringClass();
		MetricName metricName = new MetricName(getGroup(c.group(), klass), getType(c.type(), klass), getName(c.name(),
				m));
		this.counter = Metrics.newCounter(metricName);
	}

	public void inc() {
		this.counter.inc();
	}

}
