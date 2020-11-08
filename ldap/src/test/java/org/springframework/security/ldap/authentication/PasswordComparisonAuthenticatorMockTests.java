package org.springframework.security.ldap.authentication;

import javax.naming.NamingEnumeration;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.junit.Test;

import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 */
public class PasswordComparisonAuthenticatorMockTests {

	@Test
	public void ldapCompareOperationIsUsedWhenPasswordIsNotRetrieved() throws Exception {
		final DirContext dirCtx = mock(DirContext.class);
		final BaseLdapPathContextSource source = mock(BaseLdapPathContextSource.class);
		final BasicAttributes attrs = new BasicAttributes();
		attrs.put(new BasicAttribute("uid", "bob"));
		PasswordComparisonAuthenticator authenticator = new PasswordComparisonAuthenticator(source);
		authenticator.setUserDnPatterns(new String[] { "cn={0},ou=people" });
		// Get the mock to return an empty attribute set
		given(source.getReadOnlyContext()).willReturn(dirCtx);
		given(dirCtx.getAttributes(eq("cn=Bob,ou=people"), any(String[].class))).willReturn(attrs);
		given(dirCtx.getNameInNamespace()).willReturn("dc=springframework,dc=org");
		// Setup a single return value (i.e. success)
		final NamingEnumeration searchResults = new BasicAttributes("", null).getAll();
		given(dirCtx.search(eq("cn=Bob,ou=people"), eq("(userPassword={0})"), any(Object[].class),
				any(SearchControls.class))).willReturn(searchResults);
		authenticator.authenticate(new UsernamePasswordAuthenticationToken("Bob", "bobspassword"));
	}

}
