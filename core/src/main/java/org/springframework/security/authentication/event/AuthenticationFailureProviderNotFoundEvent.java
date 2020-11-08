package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to there being no
 * registered <code>AuthenticationProvider</code> that can process the request.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureProviderNotFoundEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureProviderNotFoundEvent(Authentication authentication,
			AuthenticationException exception) {
		super(authentication, exception);
	}

}
