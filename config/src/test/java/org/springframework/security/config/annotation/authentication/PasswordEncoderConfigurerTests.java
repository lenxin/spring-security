package org.springframework.security.config.annotation.authentication;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

/**
 * @author Rob Winch
 */
public class PasswordEncoderConfigurerTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void passwordEncoderRefWhenNoAuthenticationManagerBeanThenNoExceptionThrown() {
		this.spring.register(PasswordEncoderConfig.class).autowire();
	}

	@Test
	public void passwordEncoderRefWhenAuthenticationManagerBuilderThenAuthenticationSuccess() throws Exception {
		this.spring.register(PasswordEncoderNoAuthManagerLoadsConfig.class).autowire();
		this.mockMvc.perform(formLogin()).andExpect(authenticated());
	}

	@EnableWebSecurity
	static class PasswordEncoderConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			BCryptPasswordEncoder encoder = passwordEncoder();
			// @formatter:off
			auth
				.inMemoryAuthentication()
					.withUser("user").password(encoder.encode("password")).roles("USER").and()
					.passwordEncoder(encoder);
			// @formatter:on
		}

		@Override
		protected void configure(HttpSecurity http) {
		}

		@Bean
		BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

	}

	@EnableWebSecurity
	static class PasswordEncoderNoAuthManagerLoadsConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			BCryptPasswordEncoder encoder = passwordEncoder();
			// @formatter:off
			auth
				.inMemoryAuthentication()
					.withUser("user").password(encoder.encode("password")).roles("USER").and()
					.passwordEncoder(encoder);
			// @formatter:on
		}

		@Bean
		BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

	}

}
