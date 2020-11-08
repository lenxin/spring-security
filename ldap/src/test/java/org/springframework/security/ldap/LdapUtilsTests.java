package org.springframework.security.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link LdapUtils}
 *
 * @author Luke Taylor
 */
public class LdapUtilsTests {

	@Test
	public void testCloseContextSwallowsNamingException() throws Exception {
		final DirContext dirCtx = mock(DirContext.class);
		willThrow(new NamingException()).given(dirCtx).close();
		LdapUtils.closeContext(dirCtx);
	}

	@Test
	public void testGetRelativeNameReturnsEmptyStringForDnEqualToBaseName() throws Exception {
		final DirContext mockCtx = mock(DirContext.class);
		given(mockCtx.getNameInNamespace()).willReturn("dc=springframework,dc=org");
		assertThat(LdapUtils.getRelativeName("dc=springframework,dc=org", mockCtx)).isEqualTo("");
	}

	@Test
	public void testGetRelativeNameReturnsFullDnWithEmptyBaseName() throws Exception {
		final DirContext mockCtx = mock(DirContext.class);
		given(mockCtx.getNameInNamespace()).willReturn("");
		assertThat(LdapUtils.getRelativeName("cn=jane,dc=springframework,dc=org", mockCtx))
				.isEqualTo("cn=jane,dc=springframework,dc=org");
	}

	@Test
	public void testGetRelativeNameWorksWithArbitrarySpaces() throws Exception {
		final DirContext mockCtx = mock(DirContext.class);
		given(mockCtx.getNameInNamespace()).willReturn("dc=springsecurity,dc = org");
		assertThat(LdapUtils.getRelativeName("cn=jane smith, dc = springsecurity , dc=org", mockCtx))
				.isEqualTo("cn=jane smith");
	}

	@Test
	public void testRootDnsAreParsedFromUrlsCorrectly() {
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine")).isEqualTo("");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine:11389")).isEqualTo("");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine/")).isEqualTo("");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine.co.uk/")).isEqualTo("");
		assertThat(LdapUtils.parseRootDnFromUrl("ldaps://monkeymachine.co.uk/dc=springframework,dc=org"))
				.isEqualTo("dc=springframework,dc=org");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap:///dc=springframework,dc=org"))
				.isEqualTo("dc=springframework,dc=org");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine/dc=springframework,dc=org"))
				.isEqualTo("dc=springframework,dc=org");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine.co.uk/dc=springframework,dc=org/ou=blah"))
				.isEqualTo("dc=springframework,dc=org/ou=blah");
		assertThat(LdapUtils.parseRootDnFromUrl("ldap://monkeymachine.co.uk:389/dc=springframework,dc=org/ou=blah"))
				.isEqualTo("dc=springframework,dc=org/ou=blah");
	}

}
