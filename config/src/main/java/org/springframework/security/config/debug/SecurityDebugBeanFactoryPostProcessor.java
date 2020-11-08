package org.springframework.security.config.debug;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.security.config.BeanIds;
import org.springframework.security.web.debug.DebugFilter;

/**
 * @author Luke Taylor
 * @author Rob Winch
 */
public class SecurityDebugBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		this.logger.warn("\n\n" + "********************************************************************\n"
				+ "**********        Security debugging is enabled.       *************\n"
				+ "**********    This may include sensitive information.  *************\n"
				+ "**********      Do not use in a production system!     *************\n"
				+ "********************************************************************\n\n");
		// SPRING_SECURITY_FILTER_CHAIN does not exist yet since it is an alias that has
		// not been processed, so use FILTER_CHAIN_PROXY
		if (registry.containsBeanDefinition(BeanIds.FILTER_CHAIN_PROXY)) {
			BeanDefinition fcpBeanDef = registry.getBeanDefinition(BeanIds.FILTER_CHAIN_PROXY);
			BeanDefinitionBuilder debugFilterBldr = BeanDefinitionBuilder.genericBeanDefinition(DebugFilter.class);
			debugFilterBldr.addConstructorArgValue(fcpBeanDef);
			// Remove the alias to SPRING_SECURITY_FILTER_CHAIN, so that it does not
			// override the new
			// SPRING_SECURITY_FILTER_CHAIN definition
			registry.removeAlias(BeanIds.SPRING_SECURITY_FILTER_CHAIN);
			registry.registerBeanDefinition(BeanIds.SPRING_SECURITY_FILTER_CHAIN, debugFilterBldr.getBeanDefinition());
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

}
