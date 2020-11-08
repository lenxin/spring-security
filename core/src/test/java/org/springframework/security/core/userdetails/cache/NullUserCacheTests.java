package org.springframework.security.core.userdetails.cache;

import org.junit.Test;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link NullUserCache}.
 *
 * @author Ben Alex
 */
public class NullUserCacheTests {

	private User getUser() {
		return new User("john", "password", true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"));
	}

	@Test
	public void testCacheOperation() {
		NullUserCache cache = new NullUserCache();
		cache.putUserInCache(getUser());
		assertThat(cache.getUserFromCache(null)).isNull();
		cache.removeUserFromCache(null);
	}

}
