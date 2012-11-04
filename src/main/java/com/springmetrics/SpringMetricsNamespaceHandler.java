package com.springmetrics;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author pmehta
 *
 */
public class SpringMetricsNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("spring-metrics", new SpringMetricsBeanDefinitionParser());
	}
}
