package org.springframework.security.web.authentication.www;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an authentication request is rejected because the digest nonce has expired.
 *
 * @author Ben Alex
 */
public class NonceExpiredException extends AuthenticationException {

	/**
	 * Constructs a <code>NonceExpiredException</code> with the specified message.
	 * @param msg the detail message
	 */
	public NonceExpiredException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>NonceExpiredException</code> with the specified message and root
	 * cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public NonceExpiredException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
