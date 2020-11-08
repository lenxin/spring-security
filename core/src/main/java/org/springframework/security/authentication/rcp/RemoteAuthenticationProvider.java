package org.springframework.security.authentication.rcp;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Client-side object which queries a {@link RemoteAuthenticationManager} to validate an
 * authentication request.
 * <p>
 * A new <code>Authentication</code> object is created by this class comprising the
 * request <code>Authentication</code> object's <code>principal</code>,
 * <code>credentials</code> and the <code>GrantedAuthority</code>[]s returned by the
 * <code>RemoteAuthenticationManager</code>.
 * <p>
 * The <code>RemoteAuthenticationManager</code> should not require any special username or
 * password setting on the remoting client proxy factory to execute the call. Instead the
 * entire authentication request must be encapsulated solely within the
 * <code>Authentication</code> request object. In practical terms this means the
 * <code>RemoteAuthenticationManager</code> will <b>not</b> be protected by BASIC or any
 * other HTTP-level authentication.
 * </p>
 * <p>
 * If authentication fails, a <code>RemoteAuthenticationException</code> will be thrown.
 * This exception should be caught and displayed to the user, enabling them to retry with
 * alternative credentials etc.
 * </p>
 *
 * @author Ben Alex
 */
public class RemoteAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	private RemoteAuthenticationManager remoteAuthenticationManager;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.remoteAuthenticationManager, "remoteAuthenticationManager is mandatory");
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		Object credentials = authentication.getCredentials();
		String password = (credentials != null) ? credentials.toString() : null;
		Collection<? extends GrantedAuthority> authorities = this.remoteAuthenticationManager
				.attemptAuthentication(username, password);
		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	public RemoteAuthenticationManager getRemoteAuthenticationManager() {
		return this.remoteAuthenticationManager;
	}

	public void setRemoteAuthenticationManager(RemoteAuthenticationManager remoteAuthenticationManager) {
		this.remoteAuthenticationManager = remoteAuthenticationManager;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
