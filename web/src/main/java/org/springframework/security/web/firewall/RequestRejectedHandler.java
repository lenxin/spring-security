package org.springframework.security.web.firewall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used by {@link org.springframework.security.web.FilterChainProxy} to handle an
 * <code>RequestRejectedException</code>.
 *
 * @author Leonard Brünings
 * @since 5.4
 */
public interface RequestRejectedHandler {

	/**
	 * Handles an request rejected failure.
	 * @param request that resulted in an <code>RequestRejectedException</code>
	 * @param response so that the user agent can be advised of the failure
	 * @param requestRejectedException that caused the invocation
	 * @throws IOException in the event of an IOException
	 * @throws ServletException in the event of a ServletException
	 */
	void handle(HttpServletRequest request, HttpServletResponse response,
			RequestRejectedException requestRejectedException) throws IOException, ServletException;

}
