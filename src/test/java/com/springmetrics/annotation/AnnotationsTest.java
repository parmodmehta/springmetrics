package com.springmetrics.annotation;

import static org.junit.Assert.assertNotNull;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.support.JmxUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pmehta
 *
 */
@ContextConfiguration({"classpath:testContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnotationsTest {
	
	@Autowired
	private TestBean testBean;

	@Test
	public void testCounterAnnotationOnClass() throws Exception {
		testBean.counterAnnotationOnClass();
		MBeanServer server = JmxUtils.locateMBeanServer();
		assertNotNull(server.getAttribute(new ObjectName("testAnnotationGroup:type=testCounterAnnotation,name=testCounterAnnotationClass"), "Count"));
	}
	
	@Test
	public void testTimedAnnotationOnClass() throws Exception {
		testBean.timedAnnotationOnClass();
		MBeanServer server = JmxUtils.locateMBeanServer();
		assertNotNull(server.getAttribute(new ObjectName("testAnnotationGroup:type=typeTimedAnnotation,name=testTimedAnnotationClass"), "Count"));
	}
	
	@Test
	public void testExceptionCounterAnnotationOnClass() throws Exception {
		try {
			testBean.exceptionCounterOnClass(); 
		} catch(IllegalArgumentException e) {
			MBeanServer server = JmxUtils.locateMBeanServer();
			assertNotNull(server.getAttribute(new ObjectName("testAnnotationGroup:type=java.lang.IllegalArgumentException,name=testExceptionCounterAnnotationClass"), "Count"));
		}
	}

}
