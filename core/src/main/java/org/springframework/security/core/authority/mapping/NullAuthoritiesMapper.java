package org.springframework.security.core.authority.mapping;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Luke Taylor
 */
public class NullAuthoritiesMapper implements GrantedAuthoritiesMapper {

	@Override
	public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
		return authorities;
	}

}
