package org.springframework.security.web.authentication.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Uses {@code HttpServletRequest.changeSessionId()} to protect against session fixation
 * attacks. This is the default implementation.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class ChangeSessionIdAuthenticationStrategy extends AbstractSessionFixationProtectionStrategy {

	@Override
	HttpSession applySessionFixation(HttpServletRequest request) {
		request.changeSessionId();
		return request.getSession();
	}

}
