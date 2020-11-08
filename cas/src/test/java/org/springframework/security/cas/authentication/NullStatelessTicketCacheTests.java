package org.springframework.security.cas.authentication;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for the @link {@link NullStatelessTicketCache}
 *
 * @author Scott Battaglia
 *
 */
public class NullStatelessTicketCacheTests extends AbstractStatelessTicketCacheTests {

	private StatelessTicketCache cache = new NullStatelessTicketCache();

	@Test
	public void testGetter() {
		assertThat(this.cache.getByTicketId(null)).isNull();
		assertThat(this.cache.getByTicketId("test")).isNull();
	}

	@Test
	public void testInsertAndGet() {
		final CasAuthenticationToken token = getToken();
		this.cache.putTicketInCache(token);
		assertThat(this.cache.getByTicketId((String) token.getCredentials())).isNull();
	}

}
