package org.springframework.security.cas.authentication;

import java.util.ArrayList;
import java.util.List;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * @author Scott Battaglia
 * @since 2.0
 *
 */
public abstract class AbstractStatelessTicketCacheTests {

	protected CasAuthenticationToken getToken() {
		List<String> proxyList = new ArrayList<>();
		proxyList.add("https://localhost/newPortal/login/cas");
		User user = new User("rod", "password", true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"));
		final Assertion assertion = new AssertionImpl("rod");
		return new CasAuthenticationToken("key", user, "ST-0-ER94xMJmn6pha35CQRoZ",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"), user, assertion);
	}

}
