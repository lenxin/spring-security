package org.springframework.security.config.http;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

/**
 * Defines the {@link RequestMatcher} types supported by the namespace.
 *
 * @author Luke Taylor
 * @since 3.1
 */
public enum MatcherType {

	ant(AntPathRequestMatcher.class), regex(RegexRequestMatcher.class), ciRegex(RegexRequestMatcher.class), mvc(
			MvcRequestMatcher.class);

	private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME = "mvcHandlerMappingIntrospector";

	private static final String ATT_MATCHER_TYPE = "request-matcher";

	final Class<? extends RequestMatcher> type;

	MatcherType(Class<? extends RequestMatcher> type) {
		this.type = type;
	}

	public BeanDefinition createMatcher(ParserContext pc, String path, String method) {
		return createMatcher(pc, path, method, null);
	}

	public BeanDefinition createMatcher(ParserContext pc, String path, String method, String servletPath) {
		if (("/**".equals(path) || "**".equals(path)) && method == null) {
			return new RootBeanDefinition(AnyRequestMatcher.class);
		}
		BeanDefinitionBuilder matcherBldr = BeanDefinitionBuilder.rootBeanDefinition(this.type);
		if (this == mvc) {
			matcherBldr.addConstructorArgValue(new RootBeanDefinition(HandlerMappingIntrospectorFactoryBean.class));
		}
		matcherBldr.addConstructorArgValue(path);
		if (this == mvc) {
			matcherBldr.addPropertyValue("method", method);
			matcherBldr.addPropertyValue("servletPath", servletPath);
		}
		else {
			matcherBldr.addConstructorArgValue(method);
		}
		if (this == ciRegex) {
			matcherBldr.addConstructorArgValue(true);
		}
		return matcherBldr.getBeanDefinition();
	}

	static MatcherType fromElement(Element elt) {
		if (StringUtils.hasText(elt.getAttribute(ATT_MATCHER_TYPE))) {
			return valueOf(elt.getAttribute(ATT_MATCHER_TYPE));
		}

		return ant;
	}

}
