package org.springframework.security.test.context.showcase;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Rob Winch
 */
public class CustomUserDetails implements UserDetails {

	private final String name;

	private final String username;

	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(String name, String username) {
		this.name = name;
		this.username = username;
		this.authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "CustomUserDetails{" + "username='" + this.username + '\'' + '}';
	}

}
