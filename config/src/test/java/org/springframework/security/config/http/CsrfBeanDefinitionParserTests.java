package org.springframework.security.config.http;

import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ankur Pathak
 */
public class CsrfBeanDefinitionParserTests {

	private static final String CONFIG_LOCATION_PREFIX = "classpath:org/springframework/security/config/http/CsrfBeanDefinitionParserTests";

	@Test
	public void registerDataValueProcessorOnlyIfNotRegistered() {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext()) {
			context.setAllowBeanDefinitionOverriding(false);
			context.setConfigLocation(this.xml("RegisterDataValueProcessorOnyIfNotRegistered"));
			context.refresh();
		}
	}

	private String xml(String configName) {
		return CONFIG_LOCATION_PREFIX + "-" + configName + ".xml";
	}

}
