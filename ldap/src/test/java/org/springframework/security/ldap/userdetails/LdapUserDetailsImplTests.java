package org.springframework.security.ldap.userdetails;

import org.junit.Test;

import org.springframework.security.core.CredentialsContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link LdapUserDetailsImpl}
 *
 * @author Joe Grandja
 */
public class LdapUserDetailsImplTests {

	@Test
	public void credentialsAreCleared() {
		LdapUserDetailsImpl.Essence mutableLdapUserDetails = new LdapUserDetailsImpl.Essence();
		mutableLdapUserDetails.setDn("uid=username1,ou=people,dc=example,dc=com");
		mutableLdapUserDetails.setUsername("username1");
		mutableLdapUserDetails.setPassword("password");
		LdapUserDetails ldapUserDetails = mutableLdapUserDetails.createUserDetails();
		assertThat(ldapUserDetails).isInstanceOf(CredentialsContainer.class);
		ldapUserDetails.eraseCredentials();
		assertThat(ldapUserDetails.getPassword()).isNull();
	}

}
