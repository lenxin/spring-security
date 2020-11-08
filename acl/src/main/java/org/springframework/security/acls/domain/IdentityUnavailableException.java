package org.springframework.security.acls.domain;

/**
 * Thrown if an ACL identity could not be extracted from an object.
 *
 * @author Ben Alex
 */
public class IdentityUnavailableException extends RuntimeException {

	/**
	 * Constructs an <code>IdentityUnavailableException</code> with the specified message.
	 * @param msg the detail message
	 */
	public IdentityUnavailableException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>IdentityUnavailableException</code> with the specified message
	 * and root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public IdentityUnavailableException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
