package com.springmetrics.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;

import com.springmetrics.annotation.ExceptionCounter;
import com.springmetrics.proxy.ExceptionCounterDynamicProxy;
import com.springmetrics.proxy.Utils;

/**
 * @author pmehta
 *
 */
public class ExceptionCounterBeanPostProcessor extends AbstractBeanPostProcessor {

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		ClassLoader cl = bean.getClass().getClassLoader();
		@SuppressWarnings("rawtypes")
		Class[] interfaces = bean.getClass().getInterfaces();
		ExceptionCounter exceptionCounterAnnotation = (ExceptionCounter)Utils.findAnnotation(bean, ExceptionCounter.class);
		if (exceptionCounterAnnotation!=null)
			return Proxy.newProxyInstance(cl, interfaces, new ExceptionCounterDynamicProxy(bean));
		return bean;
	}
}