package org.springframework.security.web.authentication.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/**
 * Indicates a class that is able to participate in logout handling.
 *
 * <p>
 * Called by {@link LogoutFilter}.
 *
 * @author Ben Alex
 */
public interface LogoutHandler {

	/**
	 * Causes a logout to be completed. The method must complete successfully.
	 * @param request the HTTP request
	 * @param response the HTTP response
	 * @param authentication the current principal details
	 */
	void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

}
