package org.springframework.security.core.userdetails.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Used by {@link org.springframework.security.provisioning.InMemoryUserDetailsManager} to
 * temporarily store the attributes associated with a user.
 *
 * @author Ben Alex
 */
public class UserAttribute {

	private List<GrantedAuthority> authorities = new Vector<>();

	private String password;

	private boolean enabled = true;

	public void addAuthority(GrantedAuthority newAuthority) {
		this.authorities.add(newAuthority);
	}

	public List<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * Set all authorities for this user.
	 * @param authorities {@link List} &lt;{@link GrantedAuthority}&gt;
	 * @since 1.1
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Set all authorities for this user from String values. It will create the necessary
	 * {@link GrantedAuthority} objects.
	 * @param authoritiesAsStrings {@link List} &lt;{@link String}&gt;
	 * @since 1.1
	 */
	public void setAuthoritiesAsString(List<String> authoritiesAsStrings) {
		setAuthorities(new ArrayList<>(authoritiesAsStrings.size()));
		for (String authority : authoritiesAsStrings) {
			addAuthority(new SimpleGrantedAuthority(authority));
		}
	}

	public String getPassword() {
		return this.password;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isValid() {
		return (this.password != null) && (this.authorities.size() > 0);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
