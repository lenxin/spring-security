package org.springframework.security.config.annotation.authentication.ldap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.ldap.NamespaceLdapAuthenticationProviderTestsConfigs.CustomAuthoritiesPopulatorConfig;
import org.springframework.security.config.annotation.authentication.ldap.NamespaceLdapAuthenticationProviderTestsConfigs.CustomLdapAuthenticationProviderConfig;
import org.springframework.security.config.annotation.authentication.ldap.NamespaceLdapAuthenticationProviderTestsConfigs.LdapAuthenticationProviderConfig;
import org.springframework.security.config.annotation.authentication.ldap.NamespaceLdapAuthenticationProviderTestsConfigs.PasswordCompareLdapConfig;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

public class NamespaceLdapAuthenticationProviderTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FilterChainProxy filterChainProxy;

	@Test
	public void ldapAuthenticationProvider() throws Exception {
		this.spring.register(LdapAuthenticationProviderConfig.class).autowire();

		// @formatter:off
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder request = formLogin()
				.user("bob")
				.password("bobspassword");
		SecurityMockMvcResultMatchers.AuthenticatedMatcher user = authenticated()
				.withUsername("bob");
		// @formatter:on
		this.mockMvc.perform(request).andExpect(user);
	}

	@Test
	public void ldapAuthenticationProviderCustom() throws Exception {
		this.spring.register(CustomLdapAuthenticationProviderConfig.class).autowire();

		// @formatter:off
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder request = formLogin()
				.user("bob")
				.password("bobspassword");
		SecurityMockMvcResultMatchers.AuthenticatedMatcher user = authenticated()
				.withAuthorities(Collections.singleton(new SimpleGrantedAuthority("PREFIX_DEVELOPERS")));
		// @formatter:on
		this.mockMvc.perform(request).andExpect(user);
	}

	// SEC-2490
	@Test
	public void ldapAuthenticationProviderCustomLdapAuthoritiesPopulator() throws Exception {
		LdapContextSource contextSource = new DefaultSpringSecurityContextSource(
				"ldap://blah.example.com:789/dc=springframework,dc=org");
		CustomAuthoritiesPopulatorConfig.LAP = new DefaultLdapAuthoritiesPopulator(contextSource, null) {
			@Override
			protected Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) {
				return new HashSet<>(AuthorityUtils.createAuthorityList("ROLE_EXTRA"));
			}
		};

		this.spring.register(CustomAuthoritiesPopulatorConfig.class).autowire();

		// @formatter:off
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder request = formLogin()
				.user("bob")
				.password("bobspassword");
		SecurityMockMvcResultMatchers.AuthenticatedMatcher user = authenticated()
				.withAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_EXTRA")));
		// @formatter:on
		this.mockMvc.perform(request).andExpect(user);
	}

	@Test
	public void ldapAuthenticationProviderPasswordCompare() throws Exception {
		this.spring.register(PasswordCompareLdapConfig.class).autowire();

		// @formatter:off
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder request = formLogin()
				.user("bcrypt")
				.password("password");
		SecurityMockMvcResultMatchers.AuthenticatedMatcher user = authenticated().withUsername("bcrypt");
		// @formatter:on
		this.mockMvc.perform(request).andExpect(user);
	}

}
