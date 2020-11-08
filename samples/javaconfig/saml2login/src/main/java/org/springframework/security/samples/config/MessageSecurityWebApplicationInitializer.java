package org.springframework.security.samples.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * We customize {@link AbstractSecurityWebApplicationInitializer} to enable the
 * {@link HttpSessionEventPublisher}.
 *
 * @author Rob Winch
 */
public class MessageSecurityWebApplicationInitializer extends
		AbstractSecurityWebApplicationInitializer {

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
}
