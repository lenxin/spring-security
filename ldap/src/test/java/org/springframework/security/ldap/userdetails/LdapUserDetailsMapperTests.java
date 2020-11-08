package org.springframework.security.ldap.userdetails;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.junit.Test;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link LdapUserDetailsMapper}.
 *
 * @author Luke Taylor
 * @author Eddú Meléndez
 */
public class LdapUserDetailsMapperTests {

	@Test
	public void testMultipleRoleAttributeValuesAreMappedToAuthorities() {
		LdapUserDetailsMapper mapper = new LdapUserDetailsMapper();
		mapper.setConvertToUpperCase(false);
		mapper.setRolePrefix("");
		mapper.setRoleAttributes(new String[] { "userRole" });
		DirContextAdapter ctx = new DirContextAdapter();
		ctx.setAttributeValues("userRole", new String[] { "X", "Y", "Z" });
		ctx.setAttributeValue("uid", "ani");
		LdapUserDetailsImpl user = (LdapUserDetailsImpl) mapper.mapUserFromContext(ctx, "ani",
				AuthorityUtils.NO_AUTHORITIES);
		assertThat(user.getAuthorities()).hasSize(3);
	}

	/**
	 * SEC-303. Non-retrieved role attribute causes NullPointerException
	 */
	@Test
	public void testNonRetrievedRoleAttributeIsIgnored() {
		LdapUserDetailsMapper mapper = new LdapUserDetailsMapper();
		mapper.setRoleAttributes(new String[] { "userRole", "nonRetrievedAttribute" });
		BasicAttributes attrs = new BasicAttributes();
		attrs.put(new BasicAttribute("userRole", "x"));
		DirContextAdapter ctx = new DirContextAdapter(attrs, new DistinguishedName("cn=someName"));
		ctx.setAttributeValue("uid", "ani");
		LdapUserDetailsImpl user = (LdapUserDetailsImpl) mapper.mapUserFromContext(ctx, "ani",
				AuthorityUtils.NO_AUTHORITIES);
		assertThat(user.getAuthorities()).hasSize(1);
		assertThat(AuthorityUtils.authorityListToSet(user.getAuthorities())).contains("ROLE_X");
	}

	@Test
	public void testPasswordAttributeIsMappedCorrectly() {
		LdapUserDetailsMapper mapper = new LdapUserDetailsMapper();
		mapper.setPasswordAttributeName("myappsPassword");
		BasicAttributes attrs = new BasicAttributes();
		attrs.put(new BasicAttribute("myappsPassword", "mypassword".getBytes()));
		DirContextAdapter ctx = new DirContextAdapter(attrs, new DistinguishedName("cn=someName"));
		ctx.setAttributeValue("uid", "ani");
		LdapUserDetails user = (LdapUserDetailsImpl) mapper.mapUserFromContext(ctx, "ani",
				AuthorityUtils.NO_AUTHORITIES);
		assertThat(user.getPassword()).isEqualTo("mypassword");
	}

}
