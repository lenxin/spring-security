package org.springframework.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an authentication request is rejected because the credentials are invalid.
 * For this exception to be thrown, it means the account is neither locked nor disabled.
 *
 * @author Ben Alex
 */
public class BadCredentialsException extends AuthenticationException {

	/**
	 * Constructs a <code>BadCredentialsException</code> with the specified message.
	 * @param msg the detail message
	 */
	public BadCredentialsException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>BadCredentialsException</code> with the specified message and
	 * root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public BadCredentialsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
