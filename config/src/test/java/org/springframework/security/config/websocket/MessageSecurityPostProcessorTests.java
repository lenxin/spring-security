package org.springframework.security.config.websocket;

import org.junit.Test;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;

public class MessageSecurityPostProcessorTests {

	private WebSocketMessageBrokerSecurityBeanDefinitionParser.MessageSecurityPostProcessor postProcessor = new WebSocketMessageBrokerSecurityBeanDefinitionParser.MessageSecurityPostProcessor(
			"id", false);

	@Test
	public void handlesBeansWithoutClass() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		registry.registerBeanDefinition("beanWithoutClass", new GenericBeanDefinition());
		this.postProcessor.postProcessBeanDefinitionRegistry(registry);
	}

}
