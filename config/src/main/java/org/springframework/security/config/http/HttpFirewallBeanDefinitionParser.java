package org.springframework.security.config.http;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.config.BeanIds;
import org.springframework.util.StringUtils;

/**
 * Injects the supplied {@code HttpFirewall} bean reference into the
 * {@code FilterChainProxy}.
 *
 * @author Luke Taylor
 */
public class HttpFirewallBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext pc) {
		String ref = element.getAttribute("ref");
		if (!StringUtils.hasText(ref)) {
			pc.getReaderContext().error("ref attribute is required", pc.extractSource(element));
		}
		// Ensure the FCP is registered.
		HttpSecurityBeanDefinitionParser.registerFilterChainProxyIfNecessary(pc, pc.extractSource(element));
		BeanDefinition filterChainProxy = pc.getRegistry().getBeanDefinition(BeanIds.FILTER_CHAIN_PROXY);
		filterChainProxy.getPropertyValues().addPropertyValue("firewall", new RuntimeBeanReference(ref));
		return null;
	}

}
