package org.springframework.security.config.authentication;

import java.util.Collections;
import java.util.Map;

import org.w3c.dom.Element;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * Stateful parser for the &lt;password-encoder&gt; element.
 *
 * @author Luke Taylor
 */
public class PasswordEncoderParser {

	static final String ATT_REF = "ref";

	public static final String ATT_HASH = "hash";

	static final String ATT_BASE_64 = "base64";

	static final String OPT_HASH_BCRYPT = "bcrypt";

	private static final Map<String, Class<?>> ENCODER_CLASSES = Collections.singletonMap(OPT_HASH_BCRYPT,
			BCryptPasswordEncoder.class);

	private BeanMetadataElement passwordEncoder;

	public PasswordEncoderParser(Element element, ParserContext parserContext) {
		parse(element, parserContext);
	}

	private void parse(Element element, ParserContext parserContext) {
		if (element == null) {
			if (parserContext.getRegistry().containsBeanDefinition("passwordEncoder")) {
				this.passwordEncoder = parserContext.getRegistry().getBeanDefinition("passwordEncoder");
			}
			return;
		}
		String hash = element.getAttribute(ATT_HASH);
		boolean useBase64 = StringUtils.hasText(element.getAttribute(ATT_BASE_64))
				&& Boolean.parseBoolean(element.getAttribute(ATT_BASE_64));
		String ref = element.getAttribute(ATT_REF);
		if (StringUtils.hasText(ref)) {
			this.passwordEncoder = new RuntimeBeanReference(ref);
		}
		else {
			this.passwordEncoder = createPasswordEncoderBeanDefinition(hash, useBase64);
			((RootBeanDefinition) this.passwordEncoder).setSource(parserContext.extractSource(element));
		}
	}

	public static BeanDefinition createPasswordEncoderBeanDefinition(String hash, boolean useBase64) {
		Class<?> beanClass = ENCODER_CLASSES.get(hash);
		BeanDefinitionBuilder beanBldr = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
		return beanBldr.getBeanDefinition();
	}

	public BeanMetadataElement getPasswordEncoder() {
		return this.passwordEncoder;
	}

}
