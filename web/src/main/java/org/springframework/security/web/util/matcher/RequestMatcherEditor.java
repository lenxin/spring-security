package org.springframework.security.web.util.matcher;

import java.beans.PropertyEditorSupport;

import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;

/**
 * PropertyEditor which creates ELRequestMatcher instances from Strings
 *
 * This allows to use a String in a BeanDefinition instead of an (inner) bean if a
 * RequestMatcher is required, e.g. in {@link DelegatingAuthenticationEntryPoint}
 *
 * @author Mike Wiesner
 * @since 3.0.2
 */
public class RequestMatcherEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(new ELRequestMatcher(text));
	}

}
