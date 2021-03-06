package org.springframework.security.core.userdetails.cache;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests
 * {@link org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache}.
 *
 * @author Marten Deinum
 * @since 3.2
 *
 */
public class SpringCacheBasedUserCacheTests {

	private static CacheManager cacheManager;

	@BeforeClass
	public static void initCacheManaer() {
		cacheManager = new ConcurrentMapCacheManager();
		cacheManager.getCache("springbasedusercachetests");
	}

	@AfterClass
	public static void shutdownCacheManager() {
	}

	private Cache getCache() {
		Cache cache = cacheManager.getCache("springbasedusercachetests");
		cache.clear();
		return cache;
	}

	private User getUser() {
		return new User("john", "password", true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"));
	}

	@Test
	public void cacheOperationsAreSuccessful() throws Exception {
		SpringCacheBasedUserCache cache = new SpringCacheBasedUserCache(getCache());
		// Check it gets stored in the cache
		cache.putUserInCache(getUser());
		assertThat(getUser().getPassword()).isEqualTo(cache.getUserFromCache(getUser().getUsername()).getPassword());
		// Check it gets removed from the cache
		cache.removeUserFromCache(getUser());
		assertThat(cache.getUserFromCache(getUser().getUsername())).isNull();
		// Check it doesn't return values for null or unknown users
		assertThat(cache.getUserFromCache(null)).isNull();
		assertThat(cache.getUserFromCache("UNKNOWN_USER")).isNull();
	}

	@Test
	public void startupDetectsMissingCache() throws Exception {
		assertThatIllegalArgumentException().isThrownBy(() -> new SpringCacheBasedUserCache(null));
	}

}
