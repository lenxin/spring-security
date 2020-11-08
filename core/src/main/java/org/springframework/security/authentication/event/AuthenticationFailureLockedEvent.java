package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to the user's account
 * having been locked.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureLockedEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureLockedEvent(Authentication authentication, AuthenticationException exception) {
		super(authentication, exception);
	}

}
