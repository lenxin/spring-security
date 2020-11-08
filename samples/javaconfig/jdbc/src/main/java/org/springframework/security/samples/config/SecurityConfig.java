package org.springframework.security.samples.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;

	// @formatter:off
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
				.dataSource(dataSource)
				.withDefaultSchema()
				.withUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER"));
	}
	// @formatter:on
}
