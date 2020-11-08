package org.springframework.security.access.vote;

import org.junit.Test;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.TestingAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleHierarchyVoterTests {

	@Test
	public void hierarchicalRoleIsIncludedInDecision() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_A > ROLE_B");
		// User has role A, role B is required
		TestingAuthenticationToken auth = new TestingAuthenticationToken("user", "password", "ROLE_A");
		RoleHierarchyVoter voter = new RoleHierarchyVoter(roleHierarchyImpl);
		assertThat(voter.vote(auth, new Object(), SecurityConfig.createList("ROLE_B")))
				.isEqualTo(AccessDecisionVoter.ACCESS_GRANTED);
	}

}
