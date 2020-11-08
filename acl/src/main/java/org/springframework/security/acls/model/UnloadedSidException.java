package org.springframework.security.acls.model;

/**
 * Thrown if an {@link Acl} cannot perform an operation because it only loaded a subset of
 * <code>Sid</code>s and the caller has requested details for an unloaded <code>Sid</code>
 * .
 *
 * @author Ben Alex
 */
public class UnloadedSidException extends AclDataAccessException {

	/**
	 * Constructs an <code>NotFoundException</code> with the specified message.
	 * @param msg the detail message
	 */
	public UnloadedSidException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>NotFoundException</code> with the specified message and root
	 * cause.
	 * @param msg the detail message
	 * @param cause root cause
	 */
	public UnloadedSidException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
