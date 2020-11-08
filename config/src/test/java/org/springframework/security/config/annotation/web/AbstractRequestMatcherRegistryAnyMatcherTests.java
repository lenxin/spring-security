package org.springframework.security.config.annotation.web;

import org.junit.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link AbstractRequestMatcherRegistry}.
 *
 * @author Ankur Pathak
 */
public class AbstractRequestMatcherRegistryAnyMatcherTests {

	@Test
	public void antMatchersCanNotWorkAfterAnyRequest() {
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> loadConfig(AntMatchersAfterAnyRequestConfig.class));
	}

	@Test
	public void mvcMatchersCanNotWorkAfterAnyRequest() {
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> loadConfig(MvcMatchersAfterAnyRequestConfig.class));
	}

	@Test
	public void regexMatchersCanNotWorkAfterAnyRequest() {
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> loadConfig(RegexMatchersAfterAnyRequestConfig.class));
	}

	@Test
	public void anyRequestCanNotWorkAfterItself() {
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> loadConfig(AnyRequestAfterItselfConfig.class));
	}

	@Test
	public void requestMatchersCanNotWorkAfterAnyRequest() {
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> loadConfig(RequestMatchersAfterAnyRequestConfig.class));
	}

	private void loadConfig(Class<?>... configs) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setAllowCircularReferences(false);
		context.register(configs);
		context.setServletContext(new MockServletContext());
		context.refresh();
	}

	@EnableWebSecurity
	static class AntMatchersAfterAnyRequestConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
				.anyRequest().authenticated()
				.antMatchers("/demo/**").permitAll();
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class MvcMatchersAfterAnyRequestConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
				.anyRequest().authenticated()
				.mvcMatchers("/demo/**").permitAll();
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class RegexMatchersAfterAnyRequestConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
				.anyRequest().authenticated()
				.regexMatchers(".*").permitAll();
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class AnyRequestAfterItselfConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
				.anyRequest().authenticated()
				.anyRequest().permitAll();
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class RequestMatchersAfterAnyRequestConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
				.anyRequest().authenticated()
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
			// @formatter:on
		}

	}

}
