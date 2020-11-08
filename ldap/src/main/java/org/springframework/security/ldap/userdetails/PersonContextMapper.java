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
public class PersonContextMapper implements UserDetailsContextMapper {

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		Person.Essence p = new Person.Essence(ctx);
		p.setUsername(username);
		p.setAuthorities(authorities);
		return p.createUserDetails();
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		Assert.isInstanceOf(Person.class, user, "UserDetails must be a Person instance");
		Person p = (Person) user;
		p.populateContext(ctx);
	}

}
