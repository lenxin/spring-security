package org.springframework.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown by {@link ProviderManager} if no {@link AuthenticationProvider} could be found
 * that supports the presented {@link org.springframework.security.core.Authentication}
 * object.
 *
 * @author Ben Alex
 */
public class ProviderNotFoundException extends AuthenticationException {

	/**
	 * Constructs a <code>ProviderNotFoundException</code> with the specified message.
	 * @param msg the detail message
	 */
	public ProviderNotFoundException(String msg) {
		super(msg);
	}

}
