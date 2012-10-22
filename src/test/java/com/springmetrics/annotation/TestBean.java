package com.springmetrics.annotation;

import java.util.concurrent.TimeUnit;


/**
 * @author pmehta
 *
 */
public interface TestBean {

	void counterAnnotationOnClass();
	
	void timedAnnotationOnClass();
	
	void exceptionCounterOnClass();

	@Counter(group = "testAnnotationGroup", type = "testCounterAnnotation", name = "testCounterAnnotationInterface")	
	void counterAnnotationOnInterface();
	
	@Timed(group = "testAnnotationGroup", type = "typeTimedAnnotation", name = "testTimedAnnotationInterface", durationUnit = TimeUnit.MILLISECONDS, rateUnit = TimeUnit.SECONDS)
	void timedAnnotationOnInterface();
	
	@ExceptionCounter(group = "testAnnotationGroup", name = "testExceptionCounterAnnotationInterface", exceptions = {IllegalArgumentException.class})
	void exceptionCounterOnInterface();
	
}
