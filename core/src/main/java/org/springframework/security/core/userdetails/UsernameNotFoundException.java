package org.springframework.security.core.userdetails;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an {@link UserDetailsService} implementation cannot locate a {@link User} by
 * its username.
 *
 * @author Ben Alex
 */
public class UsernameNotFoundException extends AuthenticationException {

	/**
	 * Constructs a <code>UsernameNotFoundException</code> with the specified message.
	 * @param msg the detail message.
	 */
	public UsernameNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code UsernameNotFoundException} with the specified message and root
	 * cause.
	 * @param msg the detail message.
	 * @param cause root cause
	 */
	public UsernameNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
