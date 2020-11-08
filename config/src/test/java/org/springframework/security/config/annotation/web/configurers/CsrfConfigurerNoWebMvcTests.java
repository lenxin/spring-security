package org.springframework.security.config.annotation.web.configurers;

import org.junit.After;
import org.junit.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Rob Winch
 *
 */
public class CsrfConfigurerNoWebMvcTests {

	ConfigurableApplicationContext context;

	@After
	public void teardown() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void missingDispatcherServletPreventsCsrfRequestDataValueProcessor() {
		loadContext(EnableWebConfig.class);
		assertThat(this.context.containsBeanDefinition("requestDataValueProcessor")).isTrue();
	}

	@Test
	public void findDispatcherServletPreventsCsrfRequestDataValueProcessor() {
		loadContext(EnableWebMvcConfig.class);
		assertThat(this.context.containsBeanDefinition("requestDataValueProcessor")).isTrue();
	}

	@Test
	public void overrideCsrfRequestDataValueProcessor() {
		loadContext(EnableWebOverrideRequestDataConfig.class);
		assertThat(this.context.getBean(RequestDataValueProcessor.class).getClass())
				.isNotEqualTo(CsrfRequestDataValueProcessor.class);
	}

	private void loadContext(Class<?> configs) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(configs);
		annotationConfigApplicationContext.refresh();
		this.context = annotationConfigApplicationContext;
	}

	@EnableWebSecurity
	static class EnableWebConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) {
		}

	}

	@EnableWebSecurity
	static class EnableWebOverrideRequestDataConfig {

		@Bean
		@Primary
		RequestDataValueProcessor requestDataValueProcessor() {
			return mock(RequestDataValueProcessor.class);
		}

	}

	@EnableWebSecurity
	static class EnableWebMvcConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) {
		}

	}

}
