package org.springframework.security.test.web.servlet.request;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.web.support.WebTestUtils;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class SecurityMockMvcRequestPostProcessorsCsrfDebugFilterTests {

	@Autowired
	private WebApplicationContext wac;

	// SEC-3836
	@Test
	public void findCookieCsrfTokenRepository() {
		MockHttpServletRequest request = post("/").buildRequest(this.wac.getServletContext());
		CsrfTokenRepository csrfTokenRepository = WebTestUtils.getCsrfTokenRepository(request);
		assertThat(csrfTokenRepository).isNotNull();
		assertThat(csrfTokenRepository).isEqualTo(Config.cookieCsrfTokenRepository);
	}

	@EnableWebSecurity
	static class Config extends WebSecurityConfigurerAdapter {

		static CsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().csrfTokenRepository(cookieCsrfTokenRepository);
		}

		@Override
		public void configure(WebSecurity web) {
			// Enable the DebugFilter
			web.debug(true);
		}

	}

}
