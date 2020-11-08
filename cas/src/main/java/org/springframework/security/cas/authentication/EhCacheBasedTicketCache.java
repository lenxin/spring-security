package org.springframework.security.cas.authentication;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;

/**
 * Caches tickets using a Spring IoC defined
 * <a href="https://www.ehcache.org/">EHCACHE</a>.
 *
 * @author Ben Alex
 */
public class EhCacheBasedTicketCache implements StatelessTicketCache, InitializingBean {

	private static final Log logger = LogFactory.getLog(EhCacheBasedTicketCache.class);

	private Ehcache cache;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.cache, "cache mandatory");
	}

	@Override
	public CasAuthenticationToken getByTicketId(final String serviceTicket) {
		final Element element = this.cache.get(serviceTicket);
		logger.debug(LogMessage.of(() -> "Cache hit: " + (element != null) + "; service ticket: " + serviceTicket));
		return (element != null) ? (CasAuthenticationToken) element.getValue() : null;
	}

	public Ehcache getCache() {
		return this.cache;
	}

	@Override
	public void putTicketInCache(final CasAuthenticationToken token) {
		final Element element = new Element(token.getCredentials().toString(), token);
		logger.debug(LogMessage.of(() -> "Cache put: " + element.getKey()));
		this.cache.put(element);
	}

	@Override
	public void removeTicketFromCache(final CasAuthenticationToken token) {
		logger.debug(LogMessage.of(() -> "Cache remove: " + token.getCredentials().toString()));
		this.removeTicketFromCache(token.getCredentials().toString());
	}

	@Override
	public void removeTicketFromCache(final String serviceTicket) {
		this.cache.remove(serviceTicket);
	}

	public void setCache(final Ehcache cache) {
		this.cache = cache;
	}

}
