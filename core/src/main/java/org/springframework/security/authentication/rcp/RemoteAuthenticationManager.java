package org.springframework.security.authentication.rcp;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Allows remote clients to attempt authentication.
 *
 * @author Ben Alex
 */
public interface RemoteAuthenticationManager {

	/**
	 * Attempts to authenticate the remote client using the presented username and
	 * password. If authentication is successful, a collection of {@code GrantedAuthority}
	 * objects will be returned.
	 * <p>
	 * In order to maximise remoting protocol compatibility, a design decision was taken
	 * to operate with minimal arguments and return only the minimal amount of information
	 * required for remote clients to enable/disable relevant user interface commands etc.
	 * There is nothing preventing users from implementing their own equivalent package
	 * that works with more complex object types.
	 * @param username the username the remote client wishes to authenticate with.
	 * @param password the password the remote client wishes to authenticate with.
	 * @return all of the granted authorities the specified username and password have
	 * access to.
	 * @throws RemoteAuthenticationException if the authentication failed.
	 */
	Collection<? extends GrantedAuthority> attemptAuthentication(String username, String password)
			throws RemoteAuthenticationException;

}
