package com.springmetrics.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.springmetrics.annotation.Counter;
import com.springmetrics.annotation.ExceptionCounter;
import com.yammer.metrics.annotation.Timed;

/**
 * @author pmehta
 *
 */
public class SpringMetricsBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		Counter counterAnnotation = (Counter) Utils.findAnnotation(bean, Counter.class);
		ExceptionCounter exceptionCounterAnnotation = (ExceptionCounter) Utils.findAnnotation(bean, ExceptionCounter.class);
		Timed timedAnnotation = (Timed) Utils.findAnnotation(bean, Timed.class);

		if (counterAnnotation != null || exceptionCounterAnnotation !=null || timedAnnotation!=null)
			return Proxy.newProxyInstance(bean.getClass().getClassLoader(), 
											bean.getClass().getInterfaces(), 
											new SpringMetricsDynamicProxy(bean));
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

}
