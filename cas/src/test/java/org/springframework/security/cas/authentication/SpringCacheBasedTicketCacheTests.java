package org.springframework.security.cas.authentication;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests
 * {@link org.springframework.security.cas.authentication.SpringCacheBasedTicketCache}.
 *
 * @author Marten Deinum
 * @since 3.2
 */
public class SpringCacheBasedTicketCacheTests extends AbstractStatelessTicketCacheTests {

	private static CacheManager cacheManager;

	@BeforeClass
	public static void initCacheManaer() {
		cacheManager = new ConcurrentMapCacheManager();
		cacheManager.getCache("castickets");
	}

	@Test
	public void testCacheOperation() throws Exception {
		SpringCacheBasedTicketCache cache = new SpringCacheBasedTicketCache(cacheManager.getCache("castickets"));
		final CasAuthenticationToken token = getToken();
		// Check it gets stored in the cache
		cache.putTicketInCache(token);
		assertThat(cache.getByTicketId("ST-0-ER94xMJmn6pha35CQRoZ")).isEqualTo(token);
		// Check it gets removed from the cache
		cache.removeTicketFromCache(getToken());
		assertThat(cache.getByTicketId("ST-0-ER94xMJmn6pha35CQRoZ")).isNull();
		// Check it doesn't return values for null or unknown service tickets
		assertThat(cache.getByTicketId(null)).isNull();
		assertThat(cache.getByTicketId("UNKNOWN_SERVICE_TICKET")).isNull();
	}

	@Test
	public void testStartupDetectsMissingCache() throws Exception {
		assertThatIllegalArgumentException().isThrownBy(() -> new SpringCacheBasedTicketCache(null));
	}

}
