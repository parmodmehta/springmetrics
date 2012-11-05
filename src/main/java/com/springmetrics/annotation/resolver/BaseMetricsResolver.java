package com.springmetrics.annotation.resolver;

import java.lang.reflect.Method;

import com.yammer.metrics.core.MetricName;

/**
 * @author pmehta
 * 
 */
public class BaseMetricsResolver {

	protected String getGroup(final String group, final Class<?> klass) {
		return MetricName.chooseGroup(group, klass);
	}

	protected String getType(final String type, final Class<?> klass) {
		return MetricName.chooseType(type, klass);
	}

	protected String getName(final String name, Method m) {
		return MetricName.chooseName(name, m);
	}

}
