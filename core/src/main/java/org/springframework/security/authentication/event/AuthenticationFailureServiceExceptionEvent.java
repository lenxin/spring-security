package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to there being a problem
 * internal to the <code>AuthenticationManager</code>.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureServiceExceptionEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureServiceExceptionEvent(Authentication authentication,
			AuthenticationException exception) {
		super(authentication, exception);
	}

}
