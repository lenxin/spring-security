package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to the user's credentials
 * having expired.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureCredentialsExpiredEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureCredentialsExpiredEvent(Authentication authentication,
			AuthenticationException exception) {
		super(authentication, exception);
	}

}
