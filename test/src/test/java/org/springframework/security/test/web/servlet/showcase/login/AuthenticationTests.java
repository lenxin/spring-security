package org.springframework.security.test.web.servlet.showcase.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AuthenticationTests.Config.class)
@WebAppConfiguration
public class AuthenticationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity())
				.defaultRequest(get("/").accept(MediaType.TEXT_HTML)).build();
	}

	@Test
	public void requiresAuthentication() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isFound());
	}

	@Test
	public void httpBasicAuthenticationSuccess() throws Exception {
		this.mvc.perform(get("/secured/butnotfound").with(httpBasic("user", "password")))
				.andExpect(status().isNotFound()).andExpect(authenticated().withUsername("user"));
	}

	@Test
	public void authenticationSuccess() throws Exception {
		this.mvc.perform(formLogin()).andExpect(status().isFound()).andExpect(redirectedUrl("/"))
				.andExpect(authenticated().withUsername("user"));
	}

	@Test
	public void authenticationFailed() throws Exception {
		this.mvc.perform(formLogin().user("user").password("invalid")).andExpect(status().isFound())
				.andExpect(redirectedUrl("/login?error")).andExpect(unauthenticated());
	}

	@EnableWebSecurity
	@EnableWebMvc
	static class Config extends WebSecurityConfigurerAdapter {

		@Override
		@Bean
		public UserDetailsService userDetailsService() {
			// @formatter:off
			UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
			return new InMemoryUserDetailsManager(user);
			// @formatter:on
		}

	}

}
