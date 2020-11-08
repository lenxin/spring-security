package org.springframework.security.core.userdetails.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Caches <code>User</code> objects using a Spring IoC defined
 * <A HREF="https://www.ehcache.org/">EHCACHE</a>.
 *
 * @author Ben Alex
 */
public class EhCacheBasedUserCache implements UserCache, InitializingBean {

	private static final Log logger = LogFactory.getLog(EhCacheBasedUserCache.class);

	private Ehcache cache;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.cache, "cache mandatory");
	}

	public Ehcache getCache() {
		return this.cache;
	}

	@Override
	public UserDetails getUserFromCache(String username) {
		Element element = this.cache.get(username);
		logger.debug(LogMessage.of(() -> "Cache hit: " + (element != null) + "; username: " + username));
		return (element != null) ? (UserDetails) element.getValue() : null;
	}

	@Override
	public void putUserInCache(UserDetails user) {
		Element element = new Element(user.getUsername(), user);
		logger.debug(LogMessage.of(() -> "Cache put: " + element.getKey()));
		this.cache.put(element);
	}

	public void removeUserFromCache(UserDetails user) {
		logger.debug(LogMessage.of(() -> "Cache remove: " + user.getUsername()));
		this.removeUserFromCache(user.getUsername());
	}

	@Override
	public void removeUserFromCache(String username) {
		this.cache.remove(username);
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

}
