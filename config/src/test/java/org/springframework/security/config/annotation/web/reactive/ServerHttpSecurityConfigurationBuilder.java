package org.springframework.security.config.annotation.web.reactive;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.users.ReactiveAuthenticationTestConfiguration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

/**
 * @author Rob Winch
 * @since 5.0
 */
public final class ServerHttpSecurityConfigurationBuilder {

	private ServerHttpSecurityConfigurationBuilder() {
	}

	public static ServerHttpSecurity http() {
		return new ServerHttpSecurityConfiguration().httpSecurity();
	}

	public static ServerHttpSecurity httpWithDefaultAuthentication() {
		ReactiveUserDetailsService reactiveUserDetailsService = ReactiveAuthenticationTestConfiguration
				.userDetailsService();
		ReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
				reactiveUserDetailsService);
		return http().authenticationManager(authenticationManager);
	}

}
