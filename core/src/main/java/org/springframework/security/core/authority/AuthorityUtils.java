package org.springframework.security.core.authority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Utility method for manipulating <tt>GrantedAuthority</tt> collections etc.
 * <p>
 * Mainly intended for internal use.
 *
 * @author Luke Taylor
 */
public final class AuthorityUtils {

	public static final List<GrantedAuthority> NO_AUTHORITIES = Collections.emptyList();

	private AuthorityUtils() {
	}

	/**
	 * Creates a array of GrantedAuthority objects from a comma-separated string
	 * representation (e.g. "ROLE_A, ROLE_B, ROLE_C").
	 * @param authorityString the comma-separated string
	 * @return the authorities created by tokenizing the string
	 */
	public static List<GrantedAuthority> commaSeparatedStringToAuthorityList(String authorityString) {
		return createAuthorityList(StringUtils.tokenizeToStringArray(authorityString, ","));
	}

	/**
	 * Converts an array of GrantedAuthority objects to a Set.
	 * @return a Set of the Strings obtained from each call to
	 * GrantedAuthority.getAuthority()
	 */
	public static Set<String> authorityListToSet(Collection<? extends GrantedAuthority> userAuthorities) {
		Assert.notNull(userAuthorities, "userAuthorities cannot be null");
		Set<String> set = new HashSet<>(userAuthorities.size());
		for (GrantedAuthority authority : userAuthorities) {
			set.add(authority.getAuthority());
		}
		return set;
	}

	/**
	 * Converts authorities into a List of GrantedAuthority objects.
	 * @param authorities the authorities to convert
	 * @return a List of GrantedAuthority objects
	 */
	public static List<GrantedAuthority> createAuthorityList(String... authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities.length);
		for (String authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}
		return grantedAuthorities;
	}

}
