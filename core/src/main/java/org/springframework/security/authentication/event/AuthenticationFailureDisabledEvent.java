package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to the user's account
 * being disabled.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureDisabledEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureDisabledEvent(Authentication authentication, AuthenticationException exception) {
		super(authentication, exception);
	}

}
