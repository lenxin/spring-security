package org.springframework.security.authentication.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

public class MockUserCache implements UserCache {

	private Map<String, UserDetails> cache = new HashMap<>();

	@Override
	public UserDetails getUserFromCache(String username) {
		return this.cache.get(username);
	}

	@Override
	public void putUserInCache(UserDetails user) {
		this.cache.put(user.getUsername(), user);
	}

	@Override
	public void removeUserFromCache(String username) {
		this.cache.remove(username);
	}

}
