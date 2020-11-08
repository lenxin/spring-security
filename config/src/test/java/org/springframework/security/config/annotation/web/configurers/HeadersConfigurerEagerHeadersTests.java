package org.springframework.security.config.annotation.web.configurers;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

/**
 * Tests for {@link HeadersConfigurer}.
 *
 * @author Ankur Pathak
 */
public class HeadersConfigurerEagerHeadersTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	MockMvc mvc;

	@Test
	public void requestWhenHeadersEagerlyConfiguredThenHeadersAreWritten() throws Exception {
		this.spring.register(HeadersAtTheBeginningOfRequestConfig.class).autowire();
		this.mvc.perform(get("/").secure(true)).andExpect(header().string("X-Content-Type-Options", "nosniff"))
				.andExpect(header().string("X-Frame-Options", "DENY"))
				.andExpect(header().string("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains"))
				.andExpect(header().string(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate"))
				.andExpect(header().string(HttpHeaders.EXPIRES, "0"))
				.andExpect(header().string(HttpHeaders.PRAGMA, "no-cache"))
				.andExpect(header().string("X-XSS-Protection", "1; mode=block"));
	}

	@EnableWebSecurity
	public static class HeadersAtTheBeginningOfRequestConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.headers()
					.addObjectPostProcessor(new ObjectPostProcessor<HeaderWriterFilter>() {
						@Override
						public HeaderWriterFilter postProcess(HeaderWriterFilter filter) {
							filter.setShouldWriteHeadersEagerly(true);
							return filter;
						}
					});
			// @formatter:on
		}

	}

}
