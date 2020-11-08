package org.springframework.security.cas.userdetails;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.junit.Test;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 */
public class GrantedAuthorityFromAssertionAttributesUserDetailsServiceTests {

	@Test
	public void correctlyExtractsNamedAttributesFromAssertionAndConvertsThemToAuthorities() {
		GrantedAuthorityFromAssertionAttributesUserDetailsService uds = new GrantedAuthorityFromAssertionAttributesUserDetailsService(
				new String[] { "a", "b", "c", "d" });
		uds.setConvertToUpperCase(false);
		Assertion assertion = mock(Assertion.class);
		AttributePrincipal principal = mock(AttributePrincipal.class);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("a", Arrays.asList("role_a1", "role_a2"));
		attributes.put("b", "role_b");
		attributes.put("c", "role_c");
		attributes.put("d", null);
		attributes.put("someother", "unused");
		given(assertion.getPrincipal()).willReturn(principal);
		given(principal.getAttributes()).willReturn(attributes);
		given(principal.getName()).willReturn("somebody");
		CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(assertion, "ticket");
		UserDetails user = uds.loadUserDetails(token);
		Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities());
		assertThat(roles).containsExactlyInAnyOrder("role_a1", "role_a2", "role_b", "role_c");
	}

}
