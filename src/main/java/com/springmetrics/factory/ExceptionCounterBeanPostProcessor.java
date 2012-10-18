package com.springmetrics.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.proxy.ExceptionCounterDynamicProxy;
import com.springmetrics.proxy.Utils;

/**
 * @author pmehta
 * 
 */
public class ExceptionCounterBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		ExceptionCounter exceptionCounterAnnotation = (ExceptionCounter) Utils.findAnnotation(bean,
				ExceptionCounter.class);
		if (exceptionCounterAnnotation != null)
			return Proxy.newProxyInstance(bean.getClass().getClassLoader(), 
											bean.getClass().getInterfaces(), 
											new ExceptionCounterDynamicProxy(bean));
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

}
