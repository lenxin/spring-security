package org.springframework.security.acls.model;

/**
 * Thrown if an ACL-related object cannot be found.
 *
 * @author Ben Alex
 */
public class NotFoundException extends AclDataAccessException {

	/**
	 * Constructs an <code>NotFoundException</code> with the specified message.
	 * @param msg the detail message
	 */
	public NotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>NotFoundException</code> with the specified message and root
	 * cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public NotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
