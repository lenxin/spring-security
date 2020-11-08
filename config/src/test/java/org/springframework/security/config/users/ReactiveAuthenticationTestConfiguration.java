package org.springframework.security.config.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.PasswordEncodedUser;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

/**
 * @author Rob Winch
 * @since 5.0
 */
@Configuration
public class ReactiveAuthenticationTestConfiguration {

	@Bean
	public static ReactiveUserDetailsService userDetailsService() {
		return new MapReactiveUserDetailsService(PasswordEncodedUser.user(), PasswordEncodedUser.admin());
	}

}
