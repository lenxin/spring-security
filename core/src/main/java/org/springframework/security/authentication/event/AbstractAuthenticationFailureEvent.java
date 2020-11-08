package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * Abstract application event which indicates authentication failure for some reason.
 *
 * @author Ben Alex
 */
public abstract class AbstractAuthenticationFailureEvent extends AbstractAuthenticationEvent {

	private final AuthenticationException exception;

	public AbstractAuthenticationFailureEvent(Authentication authentication, AuthenticationException exception) {
		super(authentication);
		Assert.notNull(exception, "AuthenticationException is required");
		this.exception = exception;
	}

	public AuthenticationException getException() {
		return this.exception;
	}

}
