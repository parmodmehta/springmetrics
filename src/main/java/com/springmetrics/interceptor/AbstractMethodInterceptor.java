package com.springmetrics.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.AopUtils;

import com.springmetrics.factory.Utils;

/**
 * @author pmehta
 *
 */
public abstract class AbstractMethodInterceptor implements MethodInterceptor {

	protected Method[] methods;
	protected Object targetObject;
	
	public AbstractMethodInterceptor(Object bean) {
		this.targetObject = bean;
		this.methods = AopUtils.getTargetClass(bean).getDeclaredMethods();
	}

	protected Annotation findAnnotation(final Method method, Class<? extends Annotation> annotation) {
		Annotation found = null;
		for (Method m : methods) {
			if (Utils.equals(m,method)) {
				found = Utils.findAnnotation(targetObject, m, annotation);
				if (found!=null) 
					return found;
			}
		}
		return null;
	}
	
}
