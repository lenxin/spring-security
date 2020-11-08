package org.springframework.security.config.annotation.web.configuration;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.FatalBeanException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

/**
 * @author Joe Grandja
 */
public class Sec2515Tests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	// SEC-2515
	@Test
	public void loadConfigWhenAuthenticationManagerNotConfiguredAndRegisterBeanThenThrowFatalBeanException() {
		assertThatExceptionOfType(FatalBeanException.class)
				.isThrownBy(() -> this.spring.register(StackOverflowSecurityConfig.class).autowire());
	}

	@Test
	public void loadConfigWhenAuthenticationManagerNotConfiguredAndRegisterBeanCustomNameThenThrowFatalBeanException() {
		assertThatExceptionOfType(FatalBeanException.class)
				.isThrownBy(() -> this.spring.register(CustomBeanNameStackOverflowSecurityConfig.class).autowire());
	}

	// SEC-2549
	@Test
	public void loadConfigWhenChildClassLoaderSetThenContextLoads() {
		CanLoadWithChildConfig.AUTHENTICATION_MANAGER = mock(AuthenticationManager.class);
		this.spring.register(CanLoadWithChildConfig.class);
		AnnotationConfigWebApplicationContext context = (AnnotationConfigWebApplicationContext) this.spring
				.getContext();
		context.setClassLoader(new URLClassLoader(new URL[0], context.getClassLoader()));
		this.spring.autowire();
		assertThat(this.spring.getContext().getBean(AuthenticationManager.class)).isNotNull();
	} // SEC-2515

	@Test
	public void loadConfigWhenAuthenticationManagerConfiguredAndRegisterBeanThenContextLoads() {
		this.spring.register(SecurityConfig.class).autowire();
	}

	@EnableWebSecurity
	static class StackOverflowSecurityConfig extends WebSecurityConfigurerAdapter {

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

	}

	@EnableWebSecurity
	static class CustomBeanNameStackOverflowSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		@Bean(name = "custom")
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

	}

	@EnableWebSecurity
	static class CanLoadWithChildConfig extends WebSecurityConfigurerAdapter {

		static AuthenticationManager AUTHENTICATION_MANAGER;

		@Override
		@Bean
		public AuthenticationManager authenticationManager() {
			return AUTHENTICATION_MANAGER;
		}

	}

	@EnableWebSecurity
	static class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication();
		}

	}

}
