package org.springframework.security.oauth2.core.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author Rob Winch
 */
public final class TestOAuth2Users {

	private TestOAuth2Users() {
	}

	public static DefaultOAuth2User create() {
		String nameAttributeKey = "username";
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(nameAttributeKey, "user");
		Collection<GrantedAuthority> authorities = authorities(attributes);
		return new DefaultOAuth2User(authorities, attributes, nameAttributeKey);
	}

	private static Collection<GrantedAuthority> authorities(Map<String, Object> attributes) {
		return new LinkedHashSet<>(Arrays.asList(new OAuth2UserAuthority(attributes),
				new SimpleGrantedAuthority("SCOPE_read"), new SimpleGrantedAuthority("SCOPE_write")));
	}

}
