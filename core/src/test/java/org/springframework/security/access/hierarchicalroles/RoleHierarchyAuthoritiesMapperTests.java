package org.springframework.security.access.hierarchicalroles;

import java.util.Collection;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luke Taylor
 */
public class RoleHierarchyAuthoritiesMapperTests {

	@Test
	public void expectedAuthoritiesAreReturned() {
		RoleHierarchyImpl rh = new RoleHierarchyImpl();
		rh.setHierarchy("ROLE_A > ROLE_B\nROLE_B > ROLE_C");
		RoleHierarchyAuthoritiesMapper mapper = new RoleHierarchyAuthoritiesMapper(rh);
		Collection<? extends GrantedAuthority> authorities = mapper
				.mapAuthorities(AuthorityUtils.createAuthorityList("ROLE_A", "ROLE_D"));
		assertThat(authorities).hasSize(4);
		mapper = new RoleHierarchyAuthoritiesMapper(new NullRoleHierarchy());
		authorities = mapper.mapAuthorities(AuthorityUtils.createAuthorityList("ROLE_A", "ROLE_D"));
		assertThat(authorities).hasSize(2);
	}

}
