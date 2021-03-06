package org.springframework.security.config.annotation.web.configurers;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.PasswordEncodedUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests to verify that all the functionality of &lt;anonymous&gt; attributes is present
 *
 * @author Rob Winch
 * @author Josh Cummings
 *
 */
public class NamespaceHttpAnonymousTests {

	@Autowired
	MockMvc mvc;

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Test
	public void anonymousRequestWhenUsingDefaultAnonymousConfigurationThenUsesAnonymousAuthentication()
			throws Exception {
		this.spring.register(AnonymousConfig.class, AnonymousController.class).autowire();
		this.mvc.perform(get("/type")).andExpect(content().string(AnonymousAuthenticationToken.class.getSimpleName()));
	}

	@Test
	public void anonymousRequestWhenDisablingAnonymousThenDenies() throws Exception {
		this.spring.register(AnonymousDisabledConfig.class, AnonymousController.class).autowire();
		this.mvc.perform(get("/type")).andExpect(status().isForbidden());
	}

	@Test
	public void requestWhenAnonymousThenSendsAnonymousConfiguredAuthorities() throws Exception {
		this.spring.register(AnonymousGrantedAuthorityConfig.class, AnonymousController.class).autowire();
		this.mvc.perform(get("/type")).andExpect(content().string(AnonymousAuthenticationToken.class.getSimpleName()));
	}

	@Test
	public void anonymousRequestWhenAnonymousKeyConfiguredThenKeyIsUsed() throws Exception {
		this.spring.register(AnonymousKeyConfig.class, AnonymousController.class).autowire();
		this.mvc.perform(get("/key")).andExpect(content().string(String.valueOf("AnonymousKeyConfig".hashCode())));
	}

	@Test
	public void anonymousRequestWhenAnonymousUsernameConfiguredThenUsernameIsUsed() throws Exception {
		this.spring.register(AnonymousUsernameConfig.class, AnonymousController.class).autowire();
		this.mvc.perform(get("/principal")).andExpect(content().string("AnonymousUsernameConfig"));
	}

	@EnableWebSecurity
	static class AnonymousConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/type").anonymous()
					.anyRequest().denyAll();
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class AnonymousDisabledConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.anyRequest().permitAll()
					.and()
				.anonymous().disable();
			// @formatter:on
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth
				.inMemoryAuthentication()
					.withUser(PasswordEncodedUser.user())
					.withUser(PasswordEncodedUser.admin());
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class AnonymousGrantedAuthorityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/type").hasRole("ANON")
					.anyRequest().denyAll()
					.and()
				.anonymous()
					.authorities("ROLE_ANON");
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class AnonymousKeyConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/key").anonymous()
					.anyRequest().denyAll()
					.and()
				.anonymous().key("AnonymousKeyConfig");
			// @formatter:on
		}

	}

	@EnableWebSecurity
	static class AnonymousUsernameConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/principal").anonymous()
					.anyRequest().denyAll()
					.and()
				.anonymous().principal("AnonymousUsernameConfig");
			// @formatter:on
		}

	}

	@RestController
	static class AnonymousController {

		@GetMapping("/type")
		String type() {
			return anonymousToken().map(AnonymousAuthenticationToken::getClass).map(Class::getSimpleName).orElse(null);
		}

		@GetMapping("/key")
		String key() {
			return anonymousToken().map(AnonymousAuthenticationToken::getKeyHash).map(String::valueOf).orElse(null);
		}

		@GetMapping("/principal")
		String principal() {
			return anonymousToken().map(AnonymousAuthenticationToken::getName).orElse(null);
		}

		Optional<AnonymousAuthenticationToken> anonymousToken() {
			return Optional.of(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
					.filter((a) -> a instanceof AnonymousAuthenticationToken)
					.map(AnonymousAuthenticationToken.class::cast);
		}

	}

}
