package org.springframework.security.web.session;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Determines the behaviour of the {@code ConcurrentSessionFilter} when an expired session
 * is detected in the {@code ConcurrentSessionFilter}.
 *
 * @author Marten Deinum
 * @author Rob Winch
 * @since 4.2.0
 */
public interface SessionInformationExpiredStrategy {

	void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException;

}
