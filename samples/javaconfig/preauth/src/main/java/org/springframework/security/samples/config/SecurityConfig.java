package org.springframework.security.samples.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests((authorizeRequests) ->
				authorizeRequests
					.antMatchers("/login", "/resources/**").permitAll()
					.anyRequest().authenticated()
			)
			.jee((jee) ->
				jee
					.mappableRoles("USER", "ADMIN")
			);
	}
	// @formatter:on
}
