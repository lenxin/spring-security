package org.springframework.security.authentication.jaas;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ray Krueger
 */
public class TestAuthorityGranter implements AuthorityGranter {

	@Override
	public Set<String> grant(Principal principal) {
		Set<String> rtnSet = new HashSet<>();
		if (principal.getName().equals("TEST_PRINCIPAL")) {
			rtnSet.add("ROLE_TEST1");
			rtnSet.add("ROLE_TEST2");
		}
		return rtnSet;
	}

}
