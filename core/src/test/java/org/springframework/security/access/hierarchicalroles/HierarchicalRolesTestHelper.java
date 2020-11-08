package org.springframework.security.access.hierarchicalroles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.security.core.GrantedAuthority;

/**
 * Test helper class for the hierarchical roles tests.
 *
 * @author Michael Mayr
 */
public abstract class HierarchicalRolesTestHelper {

	public static boolean containTheSameGrantedAuthorities(Collection<? extends GrantedAuthority> authorities1,
			Collection<? extends GrantedAuthority> authorities2) {
		if (authorities1 == null && authorities2 == null) {
			return true;
		}
		if (authorities1 == null || authorities2 == null) {
			return false;
		}
		return CollectionUtils.isEqualCollection(authorities1, authorities2);
	}

	public static boolean containTheSameGrantedAuthoritiesCompareByAuthorityString(
			Collection<? extends GrantedAuthority> authorities1, Collection<? extends GrantedAuthority> authorities2) {
		if (authorities1 == null && authorities2 == null) {
			return true;
		}
		if (authorities1 == null || authorities2 == null) {
			return false;
		}
		return CollectionUtils.isEqualCollection(toCollectionOfAuthorityStrings(authorities1),
				toCollectionOfAuthorityStrings(authorities2));
	}

	public static List<String> toCollectionOfAuthorityStrings(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			return null;
		}
		List<String> result = new ArrayList<>(authorities.size());
		for (GrantedAuthority authority : authorities) {
			result.add(authority.getAuthority());
		}
		return result;
	}

	public static List<GrantedAuthority> createAuthorityList(final String... roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
		for (final String role : roles) {
			// Use non SimpleGrantedAuthority (SEC-863)
			authorities.add((GrantedAuthority) () -> role);
		}
		return authorities;
	}

}
