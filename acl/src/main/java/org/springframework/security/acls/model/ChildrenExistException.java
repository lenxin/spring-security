package org.springframework.security.acls.model;

/**
 * Thrown if an {@link Acl} cannot be deleted because children <code>Acl</code>s exist.
 *
 * @author Ben Alex
 */
public class ChildrenExistException extends AclDataAccessException {

	/**
	 * Constructs an <code>ChildrenExistException</code> with the specified message.
	 * @param msg the detail message
	 */
	public ChildrenExistException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>ChildrenExistException</code> with the specified message and
	 * root cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public ChildrenExistException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
