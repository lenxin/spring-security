package org.springframework.security.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig {

	// @formatter:off
	@Bean
	public UserDetailsService userDetailsService() {
		User.UserBuilder builder = User.withDefaultPasswordEncoder();
		UserDetails user = builder.username("user").password("password").roles("USER").build();
		UserDetails admin = builder.username("admin").password("password").roles("USER", "ADMIN").build();
		return new InMemoryUserDetailsManager(user, admin);
	}
		// @formatter:on
}
