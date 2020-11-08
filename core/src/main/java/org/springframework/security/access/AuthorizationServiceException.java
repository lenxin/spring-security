package org.springframework.security.access;

/**
 * Thrown if an authorization request could not be processed due to a system problem.
 * <p>
 * This might be thrown if an <code>AccessDecisionManager</code> implementation could not
 * locate a required method argument, for example.
 *
 * @author Ben Alex
 */
public class AuthorizationServiceException extends AccessDeniedException {

	/**
	 * Constructs an <code>AuthorizationServiceException</code> with the specified
	 * message.
	 * @param msg the detail message
	 */
	public AuthorizationServiceException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>AuthorizationServiceException</code> with the specified message
	 * and root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public AuthorizationServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
