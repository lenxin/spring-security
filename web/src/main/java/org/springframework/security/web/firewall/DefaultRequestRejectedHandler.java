package org.springframework.security.web.firewall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default implementation of {@link RequestRejectedHandler} that simply rethrows the
 * exception.
 *
 * @author Leonard Brünings
 * @since 5.4
 */
public class DefaultRequestRejectedHandler implements RequestRejectedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			RequestRejectedException requestRejectedException) throws IOException, ServletException {
		throw requestRejectedException;
	}

}
