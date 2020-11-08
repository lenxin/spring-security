package org.springframework.security.core.userdetails.cache;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Does not perform any caching.
 *
 * @author Ben Alex
 */
public class NullUserCache implements UserCache {

	@Override
	public UserDetails getUserFromCache(String username) {
		return null;
	}

	@Override
	public void putUserInCache(UserDetails user) {
	}

	@Override
	public void removeUserFromCache(String username) {
	}

}
