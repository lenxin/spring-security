package org.springframework.security.core.userdetails;

import reactor.core.publisher.Mono;

/**
 * An API for changing a {@link UserDetails} password.
 *
 * @author Rob Winch
 * @since 5.1
 */
public interface ReactiveUserDetailsPasswordService {

	/**
	 * Modify the specified user's password. This should change the user's password in the
	 * persistent user repository (datbase, LDAP etc).
	 * @param user the user to modify the password for
	 * @param newPassword the password to change to
	 * @return the updated UserDetails with the new password
	 */
	Mono<UserDetails> updatePassword(UserDetails user, String newPassword);

}
