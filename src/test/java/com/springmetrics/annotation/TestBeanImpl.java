package com.springmetrics.annotation;

import java.util.concurrent.TimeUnit;

/**
 * @author pmehta
 *
 */
public class TestBeanImpl implements TestBean {
	
	public TestBeanImpl() { }

	@Counter(group = "testAnnotationGroup", type = "testCounterAnnotation", name = "testCounterAnnotationClass")
	public void counterAnnotationOnClass() {
		// no op
	}
	
	@Timed(group = "testAnnotationGroup", type = "typeTimedAnnotation", name = "testTimedAnnotationClass", durationUnit = TimeUnit.MILLISECONDS, rateUnit = TimeUnit.SECONDS)
	public void timedAnnotationOnClass() {
		// no op
	}
	
	@ExceptionCounter(group = "testAnnotationGroup", name = "testExceptionCounterAnnotationClass", exceptions = {IllegalArgumentException.class})
	public void exceptionCounterOnClass() {
		throw new IllegalArgumentException();
	}
	
	public void counterAnnotationOnInterface() {
		// no op
	}
	
	public void timedAnnotationOnInterface() {
		// no op
	}
	
	public void exceptionCounterOnInterface() {
		// no op
	}


}

