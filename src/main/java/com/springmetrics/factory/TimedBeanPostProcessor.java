package com.springmetrics.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.springmetrics.proxy.TimedDynamicProxy;
import com.springmetrics.proxy.Utils;
import com.yammer.metrics.annotation.Timed;

/**
 * @author pmehta
 * 
 */
public class TimedBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		Timed timedAnnotation = (Timed) Utils.findAnnotation(bean, Timed.class);
		if (timedAnnotation != null)
			return Proxy.newProxyInstance(bean.getClass().getClassLoader(), 
											bean.getClass().getInterfaces(), 
											new TimedDynamicProxy(bean));
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

}
