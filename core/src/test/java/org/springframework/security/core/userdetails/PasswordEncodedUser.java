package org.springframework.security.core.userdetails;

import java.util.function.Function;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class PasswordEncodedUser {

	private static final UserDetails USER = withUsername("user").password("password").roles("USER").build();

	private static final UserDetails ADMIN = withUsername("admin").password("password").roles("USER", "ADMIN").build();

	public static UserDetails user() {
		return User.withUserDetails(USER).build();
	}

	public static UserDetails admin() {
		return User.withUserDetails(ADMIN).build();
	}

	public static User.UserBuilder builder() {
		return User.builder().passwordEncoder(passwordEncoder());
	}

	public static User.UserBuilder withUsername(String username) {
		return builder().username(username);
	}

	public static User.UserBuilder withUserDetails(UserDetails userDetails) {
		// @formatter:off
		return User
				.withUserDetails(userDetails)
				.passwordEncoder(passwordEncoder());
		// @formatter:on
	}

	private static Function<String, String> passwordEncoder() {
		return (rawPassword) -> "{noop}" + rawPassword;
	}

	protected PasswordEncodedUser() {
	}

}
