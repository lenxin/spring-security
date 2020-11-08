package org.springframework.security.access.hierarchicalroles;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * @author Luke Taylor
 */
public class RoleHierarchyAuthoritiesMapper implements GrantedAuthoritiesMapper {

	private final RoleHierarchy roleHierarchy;

	public RoleHierarchyAuthoritiesMapper(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	@Override
	public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
		return this.roleHierarchy.getReachableGrantedAuthorities(authorities);
	}

}
