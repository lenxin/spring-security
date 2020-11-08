package org.springframework.security.ldap.search;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Obtains a user's information from the LDAP directory given a login name.
 * <p>
 * May be optionally used to configure the LDAP authentication implementation when a more
 * sophisticated approach is required than just using a simple username-&gt;DN mapping.
 *
 * @author Luke Taylor
 */
public interface LdapUserSearch {

	/**
	 * Locates a single user in the directory and returns the LDAP information for that
	 * user.
	 * @param username the login name supplied to the authentication service.
	 * @return a DirContextOperations object containing the user's full DN and requested
	 * attributes.
	 * @throws UsernameNotFoundException if no user with the supplied name could be
	 * located by the search.
	 */
	DirContextOperations searchForUser(String username) throws UsernameNotFoundException;

}
