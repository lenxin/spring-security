package org.springframework.security.test.web.servlet.showcase.csrf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CsrfShowcaseTests.Config.class)
@WebAppConfiguration
public class CsrfShowcaseTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}

	@Test
	public void postWithCsrfWorks() throws Exception {
		this.mvc.perform(post("/").with(csrf())).andExpect(status().isNotFound());
	}

	@Test
	public void postWithCsrfWorksWithPut() throws Exception {
		this.mvc.perform(put("/").with(csrf())).andExpect(status().isNotFound());
	}

	@Test
	public void postWithNoCsrfForbidden() throws Exception {
		this.mvc.perform(post("/")).andExpect(status().isForbidden());
	}

	@EnableWebSecurity
	@EnableWebMvc
	static class Config extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) {
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
