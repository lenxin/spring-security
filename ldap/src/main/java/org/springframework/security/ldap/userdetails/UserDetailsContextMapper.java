package org.springframework.security.ldap.userdetails;

import java.util.Collection;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Operations to map a UserDetails object to and from a Spring LDAP
 * {@code DirContextOperations} implementation. Used by {@code LdapUserDetailsManager}
 * when loading and saving/creating user information, and also by the
 * {@code LdapAuthenticationProvider} to allow customization of the user data loaded
 * during authentication.
 *
 * @author Luke Taylor
 * @since 2.0
 */
public interface UserDetailsContextMapper {

	/**
	 * Creates a fully populated UserDetails object for use by the security framework.
	 * @param ctx the context object which contains the user information.
	 * @param username the user's supplied login name.
	 * @param authorities
	 * @return the user object.
	 */
	UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities);

	/**
	 * Reverse of the above operation. Populates a context object from the supplied user
	 * object. Called when saving a user, for example.
	 */
	void mapUserToContext(UserDetails user, DirContextAdapter ctx);

}
