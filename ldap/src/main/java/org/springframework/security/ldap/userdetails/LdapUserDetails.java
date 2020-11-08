package org.springframework.security.ldap.userdetails;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Captures the information for a user's LDAP entry.
 *
 * @author Luke Taylor
 */
public interface LdapUserDetails extends UserDetails, CredentialsContainer {

	/**
	 * The DN of the entry for this user's account.
	 * @return the user's DN
	 */
	String getDn();

}
