package org.springframework.security.authentication.rcp;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Server-side processor of a remote authentication request.
 * <p>
 * This bean requires no security interceptor to protect it. Instead, the bean uses the
 * configured <code>AuthenticationManager</code> to resolve an authentication request.
 *
 * @author Ben Alex
 */
public class RemoteAuthenticationManagerImpl implements RemoteAuthenticationManager, InitializingBean {

	private AuthenticationManager authenticationManager;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.authenticationManager, "authenticationManager is required");
	}

	@Override
	public Collection<? extends GrantedAuthority> attemptAuthentication(String username, String password)
			throws RemoteAuthenticationException {
		UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);
		try {
			return this.authenticationManager.authenticate(request).getAuthorities();
		}
		catch (AuthenticationException ex) {
			throw new RemoteAuthenticationException(ex.getMessage());
		}
	}

	protected AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
