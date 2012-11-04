package com.springmetrics.factory;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;

import com.springmetrics.annotation.Timed;
import com.springmetrics.interceptor.TimedInterceptor;

/**
 * @author pmehta
 *
 */
public class TimedBeanPostProcessor extends AbstractBeanPostProcessor {

	private static final long serialVersionUID = -1790116648466369070L;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Timed timedAnnotation = (Timed) Utils.findAnnotation(bean, Timed.class);
		if (timedAnnotation!=null)	
			return createProxy(new AnnotationMatchingPointcut(null,Timed.class),new TimedInterceptor(bean),bean);
		return bean;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
