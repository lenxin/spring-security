package org.springframework.security.config.annotation.authentication;

import javax.sql.DataSource;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

/**
 * @author Rob Winch
 */
public class NamespacePasswordEncoderTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void passwordEncoderRefWithInMemory() throws Exception {
		this.spring.register(PasswordEncoderWithInMemoryConfig.class).autowire();
		this.mockMvc.perform(formLogin()).andExpect(authenticated());
	}

	@Test
	public void passwordEncoderRefWithJdbc() throws Exception {
		this.spring.register(PasswordEncoderWithJdbcConfig.class).autowire();
		this.mockMvc.perform(formLogin()).andExpect(authenticated());
	}

	@Test
	public void passwordEncoderRefWithUserDetailsService() throws Exception {
		this.spring.register(PasswordEncoderWithUserDetailsServiceConfig.class).autowire();
		this.mockMvc.perform(formLogin()).andExpect(authenticated());
	}

	@EnableWebSecurity
	static class PasswordEncoderWithInMemoryConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// @formatter:off
			auth
				.inMemoryAuthentication()
				.withUser("user").password(encoder.encode("password")).roles("USER").and()
				.passwordEncoder(encoder);
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class PasswordEncoderWithJdbcConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// @formatter:off
			auth
				.jdbcAuthentication()
				.withDefaultSchema()
				.dataSource(dataSource())
				.withUser("user").password(encoder.encode("password")).roles("USER").and()
				.passwordEncoder(encoder);
			// @formatter:on
		}

		@Bean
		DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.HSQL).build();
		}

	}

	@EnableWebSecurity
	static class PasswordEncoderWithUserDetailsServiceConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// @formatter:off
			UserDetails user = User.withUsername("user")
				.passwordEncoder(encoder::encode)
				.password("password")
				.roles("USER")
				.build();
			// @formatter:on
			InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager(user);
			// @formatter:off
			auth
				.userDetailsService(uds)
				.passwordEncoder(encoder);
			// @formatter:on
		}

		@Bean
		DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.HSQL).build();
		}

	}

}
