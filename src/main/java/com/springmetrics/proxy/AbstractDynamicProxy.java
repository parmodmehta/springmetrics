package com.springmetrics.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.aop.support.AopUtils;

/**
 * @author pmehta
 *
 */
public abstract class AbstractDynamicProxy implements InvocationHandler {

	protected Object targetObject;
	protected Method[] methods;
	
	public AbstractDynamicProxy(final Object targetObject) {
		this.targetObject = targetObject;
		this.methods = AopUtils.getTargetClass(targetObject).getDeclaredMethods();
	}

	protected Annotation findAnnotation(final Class<? extends Annotation> annotation, final Method method) {
		Annotation found = null;
		for (Method m : methods) {
			if (Utils.equals(m,method)) {
				found = Utils.findAnnotationOnClass(m, annotation);
				if (found!=null) 
					return found;
			}
		}
		return null;
	}
	
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		Annotation annotation = getAnnotation(method);
		if (annotation==null) {
			try {
				return method.invoke(targetObject, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			} 
		}
		
		return doInvoke(proxy, method, args, annotation);
	}
	
	protected abstract Annotation getAnnotation(final Method method);
	
	protected abstract Object doInvoke(final Object proxy, final Method method, final Object[] args, final Annotation annotation) throws Throwable;
	
}
