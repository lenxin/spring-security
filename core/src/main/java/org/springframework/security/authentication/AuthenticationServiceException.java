package org.springframework.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an authentication request could not be processed due to a system problem.
 * <p>
 * This might be thrown if a backend authentication repository is unavailable, for
 * example.
 *
 * @author Ben Alex
 * @see InternalAuthenticationServiceException
 */
public class AuthenticationServiceException extends AuthenticationException {

	/**
	 * Constructs an <code>AuthenticationServiceException</code> with the specified
	 * message.
	 * @param msg the detail message
	 */
	public AuthenticationServiceException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>AuthenticationServiceException</code> with the specified
	 * message and root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public AuthenticationServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
