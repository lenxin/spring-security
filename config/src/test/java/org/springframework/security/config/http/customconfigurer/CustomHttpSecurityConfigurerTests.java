package org.springframework.security.config.http.customconfigurer;

import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterChainProxy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
public class CustomHttpSecurityConfigurerTests {

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	FilterChainProxy springSecurityFilterChain;

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockFilterChain chain;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest("GET", "");
		this.response = new MockHttpServletResponse();
		this.chain = new MockFilterChain();
		this.request.setMethod("GET");
	}

	@After
	public void cleanup() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void customConfiguerPermitAll() throws Exception {
		loadContext(Config.class);
		this.request.setPathInfo("/public/something");
		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);
		assertThat(this.response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}

	@Test
	public void customConfiguerFormLogin() throws Exception {
		loadContext(Config.class);
		this.request.setPathInfo("/requires-authentication");
		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);
		assertThat(this.response.getRedirectedUrl()).endsWith("/custom");
	}

	@Test
	public void customConfiguerCustomizeDisablesCsrf() throws Exception {
		loadContext(ConfigCustomize.class);
		this.request.setPathInfo("/public/something");
		this.request.setMethod("POST");
		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);
		assertThat(this.response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}

	@Test
	public void customConfiguerCustomizeFormLogin() throws Exception {
		loadContext(ConfigCustomize.class);
		this.request.setPathInfo("/requires-authentication");
		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);
		assertThat(this.response.getRedirectedUrl()).endsWith("/other");
	}

	private void loadContext(Class<?> clazz) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(clazz);
		context.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@EnableWebSecurity
	static class Config extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.apply(CustomConfigurer.customConfigurer())
					.loginPage("/custom");
			// @formatter:on
		}

		@Bean
		static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
			// Typically externalize this as a properties file
			Properties properties = new Properties();
			properties.setProperty("permitAllPattern", "/public/**");
			PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
			propertyPlaceholderConfigurer.setProperties(properties);
			return propertyPlaceholderConfigurer;
		}

	}

	@EnableWebSecurity
	static class ConfigCustomize extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.apply(CustomConfigurer.customConfigurer())
					.and()
				.csrf().disable()
				.formLogin()
					.loginPage("/other");
			// @formatter:on
		}

		@Bean
		static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
			// Typically externalize this as a properties file
			Properties properties = new Properties();
			properties.setProperty("permitAllPattern", "/public/**");
			PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
			propertyPlaceholderConfigurer.setProperties(properties);
			return propertyPlaceholderConfigurer;
		}

	}

}
