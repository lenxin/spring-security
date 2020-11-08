package org.springframework.security.web.authentication.preauth.j2ee;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author TSARDD
 * @since 18-okt-2007
 */
public class J2eePreAuthenticatedProcessingFilterTests {

	@Test
	public final void testGetPreAuthenticatedPrincipal() {
		String user = "testUser";
		assertThat(user).isEqualTo(new J2eePreAuthenticatedProcessingFilter()
				.getPreAuthenticatedPrincipal(getRequest(user, new String[] {})));
	}

	@Test
	public final void testGetPreAuthenticatedCredentials() {
		assertThat("N/A").isEqualTo(new J2eePreAuthenticatedProcessingFilter()
				.getPreAuthenticatedCredentials(getRequest("testUser", new String[] {})));
	}

	private HttpServletRequest getRequest(final String aUserName, final String[] aRoles) {
		MockHttpServletRequest req = new MockHttpServletRequest() {
			private Set<String> roles = new HashSet<>(Arrays.asList(aRoles));

			@Override
			public boolean isUserInRole(String arg0) {
				return this.roles.contains(arg0);
			}
		};
		req.setRemoteUser(aUserName);
		req.setUserPrincipal(() -> aUserName);
		return req;
	}

}
