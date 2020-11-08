package org.springframework.security.authentication.jaas;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.jaas.event.JaasAuthenticationEvent;
import org.springframework.security.authentication.jaas.event.JaasAuthenticationFailedEvent;
import org.springframework.security.authentication.jaas.event.JaasAuthenticationSuccessEvent;

/**
 * @author Ray Krueger
 */
public class JaasEventCheck implements ApplicationListener<JaasAuthenticationEvent> {

	JaasAuthenticationFailedEvent failedEvent;

	JaasAuthenticationSuccessEvent successEvent;

	@Override
	public void onApplicationEvent(JaasAuthenticationEvent event) {
		if (event instanceof JaasAuthenticationFailedEvent) {
			this.failedEvent = (JaasAuthenticationFailedEvent) event;
		}
		if (event instanceof JaasAuthenticationSuccessEvent) {
			this.successEvent = (JaasAuthenticationSuccessEvent) event;
		}
	}

}
