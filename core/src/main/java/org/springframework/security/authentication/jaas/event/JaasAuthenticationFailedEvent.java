package org.springframework.security.authentication.jaas.event;

import org.springframework.security.core.Authentication;

/**
 * Fired when LoginContext.login throws a LoginException, or if any other exception is
 * thrown during that time.
 *
 * @author Ray Krueger
 */
public class JaasAuthenticationFailedEvent extends JaasAuthenticationEvent {

	private final Exception exception;

	public JaasAuthenticationFailedEvent(Authentication auth, Exception exception) {
		super(auth);
		this.exception = exception;
	}

	public Exception getException() {
		return this.exception;
	}

}
