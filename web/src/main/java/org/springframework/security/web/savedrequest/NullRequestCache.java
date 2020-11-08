package org.springframework.security.web.savedrequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Null implementation of <tt>RequestCache</tt>. Typically used when creation of a session
 * is not desired.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public class NullRequestCache implements RequestCache {

	@Override
	public SavedRequest getRequest(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public void removeRequest(HttpServletRequest request, HttpServletResponse response) {

	}

	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public HttpServletRequest getMatchingRequest(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}
