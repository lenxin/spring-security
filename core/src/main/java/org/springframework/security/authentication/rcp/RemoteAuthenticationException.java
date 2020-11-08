package org.springframework.security.authentication.rcp;

import org.springframework.core.NestedRuntimeException;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * Thrown if a <code>RemoteAuthenticationManager</code> cannot validate the presented
 * authentication request.
 * <p>
 * This is thrown rather than the normal <code>AuthenticationException</code> because
 * <code>AuthenticationException</code> contains additional properties which may cause
 * issues for the remoting protocol.
 *
 * @author Ben Alex
 */
public class RemoteAuthenticationException extends NestedRuntimeException {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	/**
	 * Constructs a <code>RemoteAuthenticationException</code> with the specified message
	 * and no root cause.
	 * @param msg the detail message
	 */
	public RemoteAuthenticationException(String msg) {
		super(msg);
	}

}
