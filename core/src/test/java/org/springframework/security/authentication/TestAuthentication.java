package org.springframework.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.PasswordEncodedUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class TestAuthentication extends PasswordEncodedUser {

	public static Authentication authenticatedAdmin() {
		return autheticated(admin());
	}

	public static Authentication authenticatedUser() {
		return autheticated(user());
	}

	public static Authentication autheticated(UserDetails user) {
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

}
