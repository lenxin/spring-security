package org.springframework.security.web.servletapi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Internal interface for creating a {@link HttpServletRequest}.
 *
 * @author Rob Winch
 * @since 3.2
 * @see HttpServlet3RequestFactory
 */
interface HttpServletRequestFactory {

	/**
	 * Given a {@link HttpServletRequest} returns a {@link HttpServletRequest} that in
	 * most cases wraps the original {@link HttpServletRequest}.
	 * @param request the original {@link HttpServletRequest}. Cannot be null.
	 * @param response the original {@link HttpServletResponse}. Cannot be null.
	 * @return a non-null HttpServletRequest
	 */
	HttpServletRequest create(HttpServletRequest request, HttpServletResponse response);

}
