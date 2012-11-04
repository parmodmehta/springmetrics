package com.springmetrics;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.springmetrics.factory.CounterBeanPostProcessor;
import com.springmetrics.factory.ExceptionCounterBeanPostProcessor;
import com.springmetrics.factory.TimedBeanPostProcessor;

/**
 * @author pmehta
 * 
 */
public class SpringMetricsBeanDefinitionParser implements BeanDefinitionParser {

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		getComponent(parserContext,BeanDefinitionBuilder.rootBeanDefinition(CounterBeanPostProcessor.class));
		getComponent(parserContext,BeanDefinitionBuilder.rootBeanDefinition(ExceptionCounterBeanPostProcessor.class));
		getComponent(parserContext,BeanDefinitionBuilder.rootBeanDefinition(TimedBeanPostProcessor.class));
		return null;
	}

	private void getComponent(ParserContext parserContext, BeanDefinitionBuilder beanDefBuilder) {
		parserContext.registerComponent(new BeanComponentDefinition(beanDefBuilder.getBeanDefinition(), parserContext
				.getReaderContext().registerWithGeneratedName(beanDefBuilder.getBeanDefinition())));
	}

}
