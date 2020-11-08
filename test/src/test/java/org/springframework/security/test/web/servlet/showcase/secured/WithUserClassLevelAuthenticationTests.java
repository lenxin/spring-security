package org.springframework.security.test.web.servlet.showcase.secured;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WithUserClassLevelAuthenticationTests.Config.class)
@WebAppConfiguration
@WithMockUser(roles = "ADMIN")
public class WithUserClassLevelAuthenticationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}

	@Test
	public void requestProtectedUrlWithUser() throws Exception {
		this.mvc.perform(get("/"))
				// Ensure we got past Security
				.andExpect(status().isNotFound())
				// Ensure it appears we are authenticated with user
				.andExpect(authenticated().withUsername("user"));
	}

	@Test
	public void requestProtectedUrlWithAdmin() throws Exception {
		this.mvc.perform(get("/admin"))
				// Ensure we got past Security
				.andExpect(status().isNotFound())
				// Ensure it appears we are authenticated with user
				.andExpect(authenticated().withUsername("user").withRoles("ADMIN"));
	}

	@Test
	@WithAnonymousUser
	public void requestProtectedUrlWithAnonymous() throws Exception {
		this.mvc.perform(get("/"))
				// Ensure did not get past security
				.andExpect(status().isUnauthorized())
				// Ensure not authenticated
				.andExpect(unauthenticated());
	}

	@EnableWebSecurity
	@EnableWebMvc
	static class Config extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
					.and()
				.httpBasic();
			// @formatter:on
		}

		@Autowired
		void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth
				.inMemoryAuthentication()
					.withUser("user").password("password").roles("USER");
			// @formatter:on
		}

	}

}
