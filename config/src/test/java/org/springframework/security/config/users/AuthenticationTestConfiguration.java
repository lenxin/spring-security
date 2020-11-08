package org.springframework.security.config.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.PasswordEncodedUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Rob Winch
 * @since 5.0
 */
@Configuration
public class AuthenticationTestConfiguration {

	@Bean
	public static UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(PasswordEncodedUser.user(), PasswordEncodedUser.admin());
	}

}
