package com.springmetrics.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author pmehta
 *
 */
public abstract class AbstractBeanPostProcessor implements BeanPostProcessor {
	
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

}
