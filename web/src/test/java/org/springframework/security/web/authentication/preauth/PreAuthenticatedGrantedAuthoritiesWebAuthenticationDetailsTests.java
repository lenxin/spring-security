package org.springframework.security.web.authentication.preauth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author TSARDD
 */
public class PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetailsTests {

	List<GrantedAuthority> gas = AuthorityUtils.createAuthorityList("Role1", "Role2");

	@Test
	public void testToString() {
		PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails details = new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(
				getRequest("testUser", new String[] {}), this.gas);
		String toString = details.toString();
		assertThat(toString.contains("Role1")).as("toString should contain Role1").isTrue();
		assertThat(toString.contains("Role2")).as("toString should contain Role2").isTrue();
	}

	@Test
	public void testGetSetPreAuthenticatedGrantedAuthorities() {
		PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails details = new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(
				getRequest("testUser", new String[] {}), this.gas);
		List<GrantedAuthority> returnedGas = details.getGrantedAuthorities();
		assertThat(this.gas.containsAll(returnedGas) && returnedGas.containsAll(this.gas)).withFailMessage(
				"Collections do not contain same elements; expected: " + this.gas + ", returned: " + returnedGas)
				.isTrue();
	}

	private HttpServletRequest getRequest(final String userName, final String[] aRoles) {
		MockHttpServletRequest req = new MockHttpServletRequest() {
			private Set<String> roles = new HashSet<>(Arrays.asList(aRoles));

			@Override
			public boolean isUserInRole(String arg0) {
				return this.roles.contains(arg0);
			}
		};
		req.setRemoteUser(userName);
		return req;
	}

}
