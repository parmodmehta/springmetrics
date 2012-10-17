package com.springmetrics.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author pmehta
 * 
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Timed {

	String group() default "";

	String type() default "";

	String name() default "";

	TimeUnit rateUnit() default TimeUnit.SECONDS;

	TimeUnit durationUnit() default TimeUnit.MILLISECONDS;

}
