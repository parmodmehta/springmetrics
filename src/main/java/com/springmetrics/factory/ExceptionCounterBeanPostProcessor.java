package com.springmetrics.factory;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;

import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.interceptor.ExceptionCounterInterceptor;

/**
 * @author pmehta
 *
 */
public class ExceptionCounterBeanPostProcessor extends AbstractBeanPostProcessor {

	private static final long serialVersionUID = -8833200252448364396L;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		ExceptionCounter exceptionCounterAnnotation = (ExceptionCounter) Utils.findAnnotation(bean, ExceptionCounter.class);
		if (exceptionCounterAnnotation!=null)
			return createProxy(new AnnotationMatchingPointcut(null,ExceptionCounter.class),new ExceptionCounterInterceptor(bean),bean);
		return bean;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
