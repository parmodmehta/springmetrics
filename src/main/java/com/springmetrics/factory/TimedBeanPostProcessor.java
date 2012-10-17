package com.springmetrics.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;

import com.springmetrics.proxy.TimedDynamicProxy;
import com.springmetrics.proxy.Utils;
import com.yammer.metrics.annotation.Timed;

/**
 * @author pmehta
 *
 */
public class TimedBeanPostProcessor extends AbstractBeanPostProcessor  {

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		ClassLoader cl = bean.getClass().getClassLoader();
		@SuppressWarnings("rawtypes")
		Class[] interfaces = bean.getClass().getInterfaces();
		Timed timedAnnotation = (Timed)Utils.findAnnotation(bean, Timed.class);
		if (timedAnnotation!=null)
			return Proxy.newProxyInstance(cl, interfaces, new TimedDynamicProxy(bean));
		return bean;
	}

}