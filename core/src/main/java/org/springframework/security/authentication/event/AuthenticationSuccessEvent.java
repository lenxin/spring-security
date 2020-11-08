package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;

/**
 * Application event which indicates successful authentication.
 *
 * @author Ben Alex
 */
public class AuthenticationSuccessEvent extends AbstractAuthenticationEvent {

	public AuthenticationSuccessEvent(Authentication authentication) {
		super(authentication);
	}

}
