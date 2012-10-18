package com.springmetrics.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.springmetrics.annotation.Counter;
import com.springmetrics.proxy.CounterDynamicProxy;
import com.springmetrics.proxy.Utils;

/**
 * @author pmehta
 * 
 */
public class CounterBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		ClassLoader cl = bean.getClass().getClassLoader();
		@SuppressWarnings("rawtypes")
		Class[] interfaces = bean.getClass().getInterfaces();
		Counter counterAnnotation = (Counter) Utils.findAnnotation(bean, Counter.class);
		if (counterAnnotation != null)
			return Proxy.newProxyInstance(cl, interfaces, new CounterDynamicProxy(bean));
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

}
