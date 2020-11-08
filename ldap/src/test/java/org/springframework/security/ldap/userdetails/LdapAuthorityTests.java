package org.springframework.security.ldap.userdetails;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.ldap.SpringSecurityLdapTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hanik
 */
public class LdapAuthorityTests {

	public static final String DN = "cn=filip,ou=Users,dc=test,dc=com";

	LdapAuthority authority;

	@Before
	public void setUp() {
		Map<String, List<String>> attributes = new HashMap<>();
		attributes.put(SpringSecurityLdapTemplate.DN_KEY, Arrays.asList(DN));
		attributes.put("mail", Arrays.asList("filip@ldap.test.org", "filip@ldap.test2.org"));
		this.authority = new LdapAuthority("testRole", DN, attributes);
	}

	@Test
	public void testGetDn() {
		assertThat(this.authority.getDn()).isEqualTo(DN);
		assertThat(this.authority.getAttributeValues(SpringSecurityLdapTemplate.DN_KEY)).isNotNull();
		assertThat(this.authority.getAttributeValues(SpringSecurityLdapTemplate.DN_KEY)).hasSize(1);
		assertThat(this.authority.getFirstAttributeValue(SpringSecurityLdapTemplate.DN_KEY)).isEqualTo(DN);
	}

	@Test
	public void testGetAttributes() {
		assertThat(this.authority.getAttributes()).isNotNull();
		assertThat(this.authority.getAttributeValues("mail")).isNotNull();
		assertThat(this.authority.getAttributeValues("mail")).hasSize(2);
		assertThat(this.authority.getFirstAttributeValue("mail")).isEqualTo("filip@ldap.test.org");
		assertThat(this.authority.getAttributeValues("mail").get(0)).isEqualTo("filip@ldap.test.org");
		assertThat(this.authority.getAttributeValues("mail").get(1)).isEqualTo("filip@ldap.test2.org");
	}

	@Test
	public void testGetAuthority() {
		assertThat(this.authority.getAuthority()).isNotNull();
		assertThat(this.authority.getAuthority()).isEqualTo("testRole");
	}

}
