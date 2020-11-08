package org.springframework.security.ldap.authentication;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.Authentication;

/**
 * The strategy interface for locating and authenticating an Ldap user.
 * <p>
 * The LdapAuthenticationProvider calls this interface to authenticate a user and obtain
 * the information for that user from the directory.
 *
 * @author Luke Taylor
 * @see org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator
 * @see org.springframework.security.ldap.authentication.UserDetailsServiceLdapAuthoritiesPopulator
 */
public interface LdapAuthenticator {

	/**
	 * Authenticates as a user and obtains additional user information from the directory.
	 * @param authentication
	 * @return the details of the successfully authenticated user.
	 */
	DirContextOperations authenticate(Authentication authentication);

}
