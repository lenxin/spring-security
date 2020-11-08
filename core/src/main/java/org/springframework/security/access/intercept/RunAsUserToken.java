package org.springframework.security.access.intercept;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * An immutable {@link org.springframework.security.core.Authentication} implementation
 * that supports {@link RunAsManagerImpl}.
 *
 * @author Ben Alex
 */
public class RunAsUserToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Class<? extends Authentication> originalAuthentication;

	private final Object credentials;

	private final Object principal;

	private final int keyHash;

	public RunAsUserToken(String key, Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities,
			Class<? extends Authentication> originalAuthentication) {
		super(authorities);
		this.keyHash = key.hashCode();
		this.principal = principal;
		this.credentials = credentials;
		this.originalAuthentication = originalAuthentication;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	public int getKeyHash() {
		return this.keyHash;
	}

	public Class<? extends Authentication> getOriginalAuthentication() {
		return this.originalAuthentication;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		String className = (this.originalAuthentication != null) ? this.originalAuthentication.getName() : null;
		sb.append("; Original Class: ").append(className);
		return sb.toString();
	}

}
