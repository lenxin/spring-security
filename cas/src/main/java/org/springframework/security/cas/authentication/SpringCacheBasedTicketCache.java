package org.springframework.security.cas.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cache.Cache;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;

/**
 * Caches tickets using a Spring IoC defined {@link Cache}.
 *
 * @author Marten Deinum
 * @since 3.2
 *
 */
public class SpringCacheBasedTicketCache implements StatelessTicketCache {

	private static final Log logger = LogFactory.getLog(SpringCacheBasedTicketCache.class);

	private final Cache cache;

	public SpringCacheBasedTicketCache(Cache cache) {
		Assert.notNull(cache, "cache mandatory");
		this.cache = cache;
	}

	@Override
	public CasAuthenticationToken getByTicketId(final String serviceTicket) {
		final Cache.ValueWrapper element = (serviceTicket != null) ? this.cache.get(serviceTicket) : null;
		logger.debug(LogMessage.of(() -> "Cache hit: " + (element != null) + "; service ticket: " + serviceTicket));
		return (element != null) ? (CasAuthenticationToken) element.get() : null;
	}

	@Override
	public void putTicketInCache(final CasAuthenticationToken token) {
		String key = token.getCredentials().toString();
		logger.debug(LogMessage.of(() -> "Cache put: " + key));
		this.cache.put(key, token);
	}

	@Override
	public void removeTicketFromCache(final CasAuthenticationToken token) {
		logger.debug(LogMessage.of(() -> "Cache remove: " + token.getCredentials().toString()));
		this.removeTicketFromCache(token.getCredentials().toString());
	}

	@Override
	public void removeTicketFromCache(final String serviceTicket) {
		this.cache.evict(serviceTicket);
	}

}
