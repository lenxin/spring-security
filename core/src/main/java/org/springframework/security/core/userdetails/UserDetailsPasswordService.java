package org.springframework.security.core.userdetails;

/**
 * An API for changing a {@link UserDetails} password.
 *
 * @author Rob Winch
 * @since 5.1
 */
public interface UserDetailsPasswordService {

	/**
	 * Modify the specified user's password. This should change the user's password in the
	 * persistent user repository (database, LDAP etc).
	 * @param user the user to modify the password for
	 * @param newPassword the password to change to, encoded by the configured
	 * {@code PasswordEncoder}
	 * @return the updated UserDetails with the new password
	 */
	UserDetails updatePassword(UserDetails user, String newPassword);

}
