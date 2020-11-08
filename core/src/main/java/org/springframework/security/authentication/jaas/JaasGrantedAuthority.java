package org.springframework.security.authentication.jaas;

import java.security.Principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * {@code GrantedAuthority} which, in addition to the assigned role, holds the principal
 * that an {@link AuthorityGranter} used as a reason to grant this authority.
 *
 * @author Ray Krueger
 * @see AuthorityGranter
 */
public final class JaasGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String role;

	private final Principal principal;

	public JaasGrantedAuthority(String role, Principal principal) {
		Assert.notNull(role, "role cannot be null");
		Assert.notNull(principal, "principal cannot be null");
		this.role = role;
		this.principal = principal;
	}

	public Principal getPrincipal() {
		return this.principal;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof JaasGrantedAuthority) {
			JaasGrantedAuthority jga = (JaasGrantedAuthority) obj;
			return this.role.equals(jga.role) && this.principal.equals(jga.principal);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = this.principal.hashCode();
		result = 31 * result + this.role.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Jaas Authority [" + this.role + "," + this.principal + "]";
	}

}
