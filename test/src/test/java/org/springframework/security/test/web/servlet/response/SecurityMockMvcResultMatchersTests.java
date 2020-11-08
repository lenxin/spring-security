package org.springframework.security.test.web.servlet.response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SecurityMockMvcResultMatchersTests.Config.class)
@WebAppConfiguration
public class SecurityMockMvcResultMatchersTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		// @formatter:off
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(this.context)
			.apply(springSecurity())
			.build();
		// @formatter:on
	}

	@Test
	public void withAuthenticationWhenMatchesThenSuccess() throws Exception {
		this.mockMvc.perform(formLogin()).andExpect(authenticated().withAuthentication(
				(auth) -> assertThat(auth).isInstanceOf(UsernamePasswordAuthenticationToken.class)));
	}

	@Test
	public void withAuthenticationWhenNotMatchesThenFails() throws Exception {
		assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> this.mockMvc.perform(formLogin()).andExpect(
				authenticated().withAuthentication((auth) -> assertThat(auth.getName()).isEqualTo("notmatch"))));
	}

	// SEC-2719
	@Test
	public void withRolesNotOrderSensitive() throws Exception {
		// @formatter:off
		this.mockMvc
			.perform(formLogin())
			.andExpect(authenticated().withRoles("USER", "SELLER"))
			.andExpect(authenticated().withRoles("SELLER", "USER"));
		// @formatter:on
	}

	@Test
	public void withRolesFailsIfNotAllRoles() throws Exception {
		assertThatExceptionOfType(AssertionError.class).isThrownBy(() ->
		// @formatter:off
			this.mockMvc
				.perform(formLogin())
				.andExpect(authenticated().withRoles("USER"))
		// @formatter:on
		);
	}

	@EnableWebSecurity
	@EnableWebMvc
	static class Config extends WebSecurityConfigurerAdapter {

		@Override
		@Bean
		public UserDetailsService userDetailsService() {
			// @formatter:off
			UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER", "SELLER").build();
			// @formatter:on
			return new InMemoryUserDetailsManager(user);
		}

		@RestController
		static class Controller {

			@RequestMapping("/")
			String ok() {
				return "ok";
			}

		}

	}

}
