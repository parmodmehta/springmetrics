package com.springmetrics.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author pmehta
 *
 */
public class Utils {

	public static Annotation findAnnotation(final Object bean, final Class<? extends Annotation> annotation) {
		Method[] methods = AopUtils.getTargetClass(bean).getDeclaredMethods();
		if (methods.length==0)
			return null;
		Annotation an = null;
		for (Method m : methods) {
			an = findAnnotationOnClass(m, annotation);
			if (an!=null)
				return an;
			an = findAnnotationOnInterface(m, bean.getClass(), annotation);
			if (an!=null) 
				return an;
		}
		return null;
	}
	
	public static Annotation findAnnotation(final Object bean, final Method method, final Class<? extends Annotation> annotation) {
		Annotation an = findAnnotationOnClass(method, annotation);
		return an!=null ? an : findAnnotationOnInterface(method, bean.getClass(), annotation);
	}

	public static Annotation findAnnotationOnClass(final Method method, final Class<? extends Annotation> annotation) {
		return AnnotationUtils.findAnnotation(method, annotation);
	}

	public static Annotation findAnnotationOnInterface(final Method method, @SuppressWarnings("rawtypes") final Class targetClass, final Class<? extends Annotation> annotation) {
		try {
			@SuppressWarnings("unchecked")
			Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
			return AnnotationUtils.findAnnotation(m, annotation);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		return null;
	}
	
	public static boolean equals(final Method m1, final Method m2) {
		if (!m1.getName().equals(m2.getName())) 
			return false;
		if (!m1.getReturnType().equals(m2.getReturnType()))
		    return false;
		@SuppressWarnings("rawtypes")
		Class[] params1 = m1.getParameterTypes();
		@SuppressWarnings("rawtypes")
		Class[] params2 = m2.getParameterTypes();
		if (params1.length == params2.length) {
		    for (int i = 0; i < params1.length; i++) {
			if (params1[i] != params2[i])
			    return false;
		    }
		    return true;
		}
		return false;
	}


}
