package org.springframework.security.acls.model;

/**
 * Thrown if an <code>Acl</code> entry already exists for the object.
 *
 * @author Ben Alex
 */
public class AlreadyExistsException extends AclDataAccessException {

	/**
	 * Constructs an <code>AlreadyExistsException</code> with the specified message.
	 * @param msg the detail message
	 */
	public AlreadyExistsException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>AlreadyExistsException</code> with the specified message and
	 * root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public AlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
