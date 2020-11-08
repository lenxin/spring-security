package org.springframework.security.ldap.userdetails;

import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;

/**
 * Obtains a list of granted authorities for an Ldap user.
 * <p>
 * Used by the <tt>LdapAuthenticationProvider</tt> once a user has been authenticated to
 * create the final user details object.
 * </p>
 *
 * @author Luke Taylor
 */
public interface LdapAuthoritiesPopulator {

	/**
	 * Get the list of authorities for the user.
	 * @param userData the context object which was returned by the LDAP authenticator.
	 * @return the granted authorities for the given user.
	 *
	 */
	Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username);

}
