package org.springframework.security.config.annotation.authentication;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.core.userdetails.PasswordEncodedUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

/**
 * @author Rob Winch
 */
public class NamespaceAuthenticationProviderTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	private MockMvc mockMvc;

	@Test
	// authentication-provider@ref
	public void authenticationProviderRef() throws Exception {
		this.spring.register(AuthenticationProviderRefConfig.class).autowire();
		this.mockMvc.perform(formLogin()).andExpect(authenticated().withUsername("user"));
	}

	@Test
	// authentication-provider@user-service-ref
	public void authenticationProviderUserServiceRef() throws Exception {
		this.spring.register(AuthenticationProviderRefConfig.class).autowire();
		this.mockMvc.perform(formLogin()).andExpect(authenticated().withUsername("user"));
	}

	@EnableWebSecurity
	static class AuthenticationProviderRefConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) {
			// @formatter:off
			auth
				.authenticationProvider(authenticationProvider());
			// @formatter:on
		}

		@Bean
		DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider result = new DaoAuthenticationProvider();
			result.setUserDetailsService(new InMemoryUserDetailsManager(PasswordEncodedUser.user()));
			return result;
		}

	}

	@EnableWebSecurity
	static class UserServiceRefConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth
				.userDetailsService(userDetailsService());
			// @formatter:on
		}

		@Override
		@Bean
		public UserDetailsService userDetailsService() {
			return new InMemoryUserDetailsManager(PasswordEncodedUser.user());
		}

	}

}
