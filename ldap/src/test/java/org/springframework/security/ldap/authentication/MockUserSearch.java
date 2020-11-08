package org.springframework.security.ldap.authentication;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.ldap.search.LdapUserSearch;

/**
 * @author Luke Taylor
 */
public class MockUserSearch implements LdapUserSearch {

	DirContextOperations user;

	public MockUserSearch() {
	}

	public MockUserSearch(DirContextOperations user) {
		this.user = user;
	}

	@Override
	public DirContextOperations searchForUser(String username) {
		return this.user;
	}

}
