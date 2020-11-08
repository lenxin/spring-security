package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to invalid credentials
 * being presented.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureBadCredentialsEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureBadCredentialsEvent(Authentication authentication, AuthenticationException exception) {
		super(authentication, exception);
	}

}
