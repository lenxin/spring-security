package org.springframework.security.web.firewall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.log.LogMessage;

/**
 * A simple implementation of {@link RequestRejectedHandler} that sends an error with
 * configurable status code.
 *
 * @author Leonard Brünings
 * @since 5.4
 */
public class HttpStatusRequestRejectedHandler implements RequestRejectedHandler {

	private static final Log logger = LogFactory.getLog(HttpStatusRequestRejectedHandler.class);

	private final int httpError;

	/**
	 * Constructs an instance which uses {@code 400} as response code.
	 */
	public HttpStatusRequestRejectedHandler() {
		this.httpError = HttpServletResponse.SC_BAD_REQUEST;
	}

	/**
	 * Constructs an instance which uses a configurable http code as response.
	 * @param httpError http status code to use
	 */
	public HttpStatusRequestRejectedHandler(int httpError) {
		this.httpError = httpError;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			RequestRejectedException requestRejectedException) throws IOException {
		logger.debug(LogMessage.format("Rejecting request due to: %s", requestRejectedException.getMessage()),
				requestRejectedException);
		response.sendError(this.httpError);
	}

}
