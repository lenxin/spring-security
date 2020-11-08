package org.springframework.security.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// @formatter:off
	@Autowired
	public void configureGlobal(
			AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
	// @formatter:on

	// @formatter:off
	@Override
	protected void configure(
			HttpSecurity http) throws Exception {
		http
			.authorizeRequests((authorizeRequests) ->
				authorizeRequests
					.anyRequest().authenticated()
			)
			.formLogin(withDefaults())
			.sessionManagement((sessionManagement) ->
				sessionManagement
					.sessionConcurrency((sessionConcurrency) ->
						sessionConcurrency
							.maximumSessions(1)
							.expiredUrl("/login?expired")
					)
			);
	}
	// @formatter:on
}
