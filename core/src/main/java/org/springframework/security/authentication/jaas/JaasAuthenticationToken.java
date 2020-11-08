package org.springframework.security.authentication.jaas;

import java.util.List;

import javax.security.auth.login.LoginContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * UsernamePasswordAuthenticationToken extension to carry the Jaas LoginContext that the
 * user was logged into
 *
 * @author Ray Krueger
 */
public class JaasAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final transient LoginContext loginContext;

	public JaasAuthenticationToken(Object principal, Object credentials, LoginContext loginContext) {
		super(principal, credentials);
		this.loginContext = loginContext;
	}

	public JaasAuthenticationToken(Object principal, Object credentials, List<GrantedAuthority> authorities,
			LoginContext loginContext) {
		super(principal, credentials, authorities);
		this.loginContext = loginContext;
	}

	public LoginContext getLoginContext() {
		return this.loginContext;
	}

}
