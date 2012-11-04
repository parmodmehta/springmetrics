package com.springmetrics.factory;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;

import com.springmetrics.annotation.Counter;
import com.springmetrics.interceptor.CounterInterceptor;

/**
 * @author pmehta
 *
 */
public class CounterBeanPostProcessor extends AbstractBeanPostProcessor {

	private static final long serialVersionUID = 2255403774846063121L;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Counter counterAnnotation = (Counter) Utils.findAnnotation(bean, Counter.class);
		if (counterAnnotation!=null)
			return createProxy(new AnnotationMatchingPointcut(null,Counter.class),new CounterInterceptor(bean),bean);
		return bean;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
