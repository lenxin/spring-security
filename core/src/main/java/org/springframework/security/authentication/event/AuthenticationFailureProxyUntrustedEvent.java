package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Application event which indicates authentication failure due to the CAS user's ticket
 * being generated by an untrusted proxy.
 *
 * @author Ben Alex
 */
public class AuthenticationFailureProxyUntrustedEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureProxyUntrustedEvent(Authentication authentication, AuthenticationException exception) {
		super(authentication, exception);
	}

}
