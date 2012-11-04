package com.springmetrics.factory;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;

/**
 * @author pmehta
 *
 */
public abstract class AbstractBeanPostProcessor extends ProxyConfig implements BeanPostProcessor {

	private static final long serialVersionUID = -1001017164873907629L;

	protected Object createProxy(Pointcut pointcut, MethodInterceptor interceptor, Object bean) {
		PointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
		if (bean instanceof Advised) {
			Advised advise = (Advised)bean;
			advise.addAdvisor(0, advisor);
			return bean;
		}
		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.addAdvisor(advisor);
		return proxyFactory.getProxy(ClassUtils.getDefaultClassLoader());
	}
}