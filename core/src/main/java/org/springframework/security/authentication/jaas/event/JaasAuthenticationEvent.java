package org.springframework.security.authentication.jaas.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

/**
 * Parent class for events fired by the
 * {@link org.springframework.security.authentication.jaas.JaasAuthenticationProvider
 * JaasAuthenticationProvider}.
 *
 * @author Ray Krueger
 */
public abstract class JaasAuthenticationEvent extends ApplicationEvent {

	/**
	 * The Authentication object is stored as the ApplicationEvent 'source'.
	 * @param auth
	 */
	public JaasAuthenticationEvent(Authentication auth) {
		super(auth);
	}

	/**
	 * Pre-casted method that returns the 'source' of the event.
	 * @return the Authentication
	 */
	public Authentication getAuthentication() {
		return (Authentication) this.source;
	}

}
