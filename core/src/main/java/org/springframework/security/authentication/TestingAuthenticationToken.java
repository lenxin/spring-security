package org.springframework.security.authentication;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * An {@link org.springframework.security.core.Authentication} implementation that is
 * designed for use whilst unit testing.
 * <p>
 * The corresponding authentication provider is {@link TestingAuthenticationProvider}.
 *
 * @author Ben Alex
 */
public class TestingAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private final Object credentials;

	private final Object principal;

	public TestingAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
	}

	public TestingAuthenticationToken(Object principal, Object credentials, String... authorities) {
		this(principal, credentials, AuthorityUtils.createAuthorityList(authorities));
	}

	public TestingAuthenticationToken(Object principal, Object credentials, List<GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
