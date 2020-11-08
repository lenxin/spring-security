package org.springframework.security.config.annotation.web.configurers;

import java.security.Principal;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests to verify that all the functionality of &lt;jee&gt; attributes is present
 *
 * @author Rob Winch
 * @author Josh Cummings
 *
 */
public class NamespaceHttpJeeTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	MockMvc mvc;

	@Test
	public void requestWhenJeeUserThenBehaviorDiffersFromNamespaceForRoleNames() throws Exception {
		this.spring.register(JeeMappableRolesConfig.class, BaseController.class).autowire();
		Principal user = mock(Principal.class);
		given(user.getName()).willReturn("joe");
		this.mvc.perform(get("/roles").principal(user).with((request) -> {
			request.addUserRole("ROLE_admin");
			request.addUserRole("ROLE_user");
			request.addUserRole("ROLE_unmapped");
			return request;
		})).andExpect(status().isOk()).andExpect(content().string("ROLE_admin,ROLE_user"));
	}

	@Test
	public void requestWhenCustomAuthenticatedUserDetailsServiceThenBehaviorMatchesNamespace() throws Exception {
		this.spring.register(JeeUserServiceRefConfig.class, BaseController.class).autowire();
		Principal user = mock(Principal.class);
		given(user.getName()).willReturn("joe");
		User result = new User(user.getName(), "N/A", true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_user"));
		given(bean(AuthenticationUserDetailsService.class).loadUserDetails(any())).willReturn(result);
		this.mvc.perform(get("/roles").principal(user)).andExpect(status().isOk())
				.andExpect(content().string("ROLE_user"));
		verifyBean(AuthenticationUserDetailsService.class).loadUserDetails(any());
	}

	private <T> T bean(Class<T> beanClass) {
		return this.spring.getContext().getBean(beanClass);
	}

	private <T> T verifyBean(Class<T> beanClass) {
		return verify(this.spring.getContext().getBean(beanClass));
	}

	@EnableWebSecurity
	public static class JeeMappableRolesConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.anyRequest().hasRole("user")
					.and()
				.jee()
					.mappableRoles("user", "admin");
			// @formatter:on
		}

	}

	@EnableWebSecurity
	public static class JeeUserServiceRefConfig extends WebSecurityConfigurerAdapter {

		private final AuthenticationUserDetailsService authenticationUserDetailsService = mock(
				AuthenticationUserDetailsService.class);

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.anyRequest().hasRole("user")
					.and()
				.jee()
					.mappableAuthorities("ROLE_user", "ROLE_admin")
					.authenticatedUserDetailsService(this.authenticationUserDetailsService);
			// @formatter:on
		}

		@Bean
		public AuthenticationUserDetailsService authenticationUserDetailsService() {
			return this.authenticationUserDetailsService;
		}

	}

	@RestController
	static class BaseController {

		@GetMapping("/authenticated")
		String authenticated(Authentication authentication) {
			return authentication.getName();
		}

		@GetMapping("/roles")
		String roles(Authentication authentication) {
			return authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(","));
		}

	}

}
