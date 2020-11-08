package org.springframework.security.ldap.userdetails;

import java.util.Collection;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * @author Luke Taylor
 */
public class InetOrgPersonContextMapper implements UserDetailsContextMapper {

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		InetOrgPerson.Essence p = new InetOrgPerson.Essence(ctx);
		p.setUsername(username);
		p.setAuthorities(authorities);
		return p.createUserDetails();

	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		Assert.isInstanceOf(InetOrgPerson.class, user, "UserDetails must be an InetOrgPerson instance");
		InetOrgPerson p = (InetOrgPerson) user;
		p.populateContext(ctx);
	}

}
