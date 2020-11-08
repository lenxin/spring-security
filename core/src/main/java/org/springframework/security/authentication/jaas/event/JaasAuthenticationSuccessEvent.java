package org.springframework.security.authentication.jaas.event;

import org.springframework.security.core.Authentication;

/**
 * Fired by the
 * {@link org.springframework.security.authentication.jaas.JaasAuthenticationProvider
 * JaasAuthenticationProvider} after successfully logging the user into the LoginContext,
 * handling all callbacks, and calling all AuthorityGranters.
 *
 * @author Ray Krueger
 */
public class JaasAuthenticationSuccessEvent extends JaasAuthenticationEvent {

	public JaasAuthenticationSuccessEvent(Authentication auth) {
		super(auth);
	}

}
