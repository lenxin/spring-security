package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Matches any supplied request.
 *
 * @author Luke Taylor
 * @since 3.1
 */
public final class AnyRequestMatcher implements RequestMatcher {

	public static final RequestMatcher INSTANCE = new AnyRequestMatcher();

	private AnyRequestMatcher() {
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean equals(Object obj) {
		return obj instanceof AnyRequestMatcher
				|| obj instanceof org.springframework.security.web.util.matcher.AnyRequestMatcher;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public String toString() {
		return "any request";
	}

}
