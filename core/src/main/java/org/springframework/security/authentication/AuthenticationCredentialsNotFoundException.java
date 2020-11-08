package org.springframework.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an authentication request is rejected because there is no
 * {@link Authentication} object in the
 * {@link org.springframework.security.core.context.SecurityContext SecurityContext}.
 *
 * @author Ben Alex
 */
public class AuthenticationCredentialsNotFoundException extends AuthenticationException {

	/**
	 * Constructs an <code>AuthenticationCredentialsNotFoundException</code> with the
	 * specified message.
	 * @param msg the detail message
	 */
	public AuthenticationCredentialsNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>AuthenticationCredentialsNotFoundException</code> with the
	 * specified message and root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public AuthenticationCredentialsNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
