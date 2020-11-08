package org.springframework.security.ldap.userdetails;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.authentication.UserDetailsServiceLdapAuthoritiesPopulator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 */
public class UserDetailsServiceLdapAuthoritiesPopulatorTests {

	@Test
	public void delegationToUserDetailsServiceReturnsCorrectRoles() {
		UserDetailsService uds = mock(UserDetailsService.class);
		UserDetails user = mock(UserDetails.class);
		given(uds.loadUserByUsername("joe")).willReturn(user);
		List authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		given(user.getAuthorities()).willReturn(authorities);
		UserDetailsServiceLdapAuthoritiesPopulator populator = new UserDetailsServiceLdapAuthoritiesPopulator(uds);
		Collection<? extends GrantedAuthority> auths = populator.getGrantedAuthorities(new DirContextAdapter(), "joe");
		assertThat(auths).hasSize(1);
		assertThat(AuthorityUtils.authorityListToSet(auths).contains("ROLE_USER")).isTrue();
	}

}
