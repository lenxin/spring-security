package org.springframework.security.ldap.userdetails;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.MockUserSearch;
import org.springframework.security.ldap.authentication.NullLdapAuthoritiesPopulator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link LdapUserDetailsService}
 *
 * @author Luke Taylor
 */
public class LdapUserDetailsServiceTests {

	@Test
	public void rejectsNullSearchObject() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new LdapUserDetailsService(null, new NullLdapAuthoritiesPopulator()));
	}

	@Test
	public void rejectsNullAuthoritiesPopulator() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LdapUserDetailsService(new MockUserSearch(), null));
	}

	@Test
	public void correctAuthoritiesAreReturned() {
		DirContextAdapter userData = new DirContextAdapter(new DistinguishedName("uid=joe"));
		LdapUserDetailsService service = new LdapUserDetailsService(new MockUserSearch(userData),
				new MockAuthoritiesPopulator());
		service.setUserDetailsMapper(new LdapUserDetailsMapper());
		UserDetails user = service.loadUserByUsername("doesntmatterwegetjoeanyway");
		Set<String> authorities = AuthorityUtils.authorityListToSet(user.getAuthorities());
		assertThat(authorities).hasSize(1);
		assertThat(authorities.contains("ROLE_FROM_POPULATOR")).isTrue();
	}

	@Test
	public void nullPopulatorConstructorReturnsEmptyAuthoritiesList() {
		DirContextAdapter userData = new DirContextAdapter(new DistinguishedName("uid=joe"));
		LdapUserDetailsService service = new LdapUserDetailsService(new MockUserSearch(userData));
		UserDetails user = service.loadUserByUsername("doesntmatterwegetjoeanyway");
		assertThat(user.getAuthorities()).isEmpty();
	}

	class MockAuthoritiesPopulator implements LdapAuthoritiesPopulator {

		@Override
		public Collection<GrantedAuthority> getGrantedAuthorities(DirContextOperations userCtx, String username) {
			return AuthorityUtils.createAuthorityList("ROLE_FROM_POPULATOR");
		}

	}

}
