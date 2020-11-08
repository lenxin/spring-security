package org.springframework.security.provisioning;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Luke Taylor
 * @since 3.1
 */
class MutableUser implements MutableUserDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String password;

	private final UserDetails delegate;

	MutableUser(UserDetails user) {
		this.delegate = user;
		this.password = user.getPassword();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.delegate.getAuthorities();
	}

	@Override
	public String getUsername() {
		return this.delegate.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.delegate.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.delegate.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.delegate.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.delegate.isEnabled();
	}

}
