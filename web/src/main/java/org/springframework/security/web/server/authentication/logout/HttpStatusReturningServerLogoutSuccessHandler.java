package org.springframework.security.web.server.authentication.logout;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.util.Assert;

/**
 * Implementation of the {@link ServerLogoutSuccessHandler}. By default returns an HTTP
 * status code of {@code 200}. This is useful in REST-type scenarios where a redirect upon
 * a successful logout is not desired.
 *
 * @author Eric Deandrea
 * @since 5.1
 */
public class HttpStatusReturningServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {

	private final HttpStatus httpStatusToReturn;

	/**
	 * Initialize the {@code HttpStatusReturningServerLogoutSuccessHandler} with a
	 * user-defined {@link HttpStatus}.
	 * @param httpStatusToReturn Must not be {@code null}.
	 */
	public HttpStatusReturningServerLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
		Assert.notNull(httpStatusToReturn, "The provided HttpStatus must not be null.");
		this.httpStatusToReturn = httpStatusToReturn;
	}

	/**
	 * Initialize the {@code HttpStatusReturningServerLogoutSuccessHandler} with the
	 * default {@link HttpStatus#OK}.
	 */
	public HttpStatusReturningServerLogoutSuccessHandler() {
		this.httpStatusToReturn = HttpStatus.OK;
	}

	/**
	 * Implementation of
	 * {@link ServerLogoutSuccessHandler#onLogoutSuccess(WebFilterExchange, Authentication)}.
	 * Sets the status on the {@link WebFilterExchange}.
	 * @param exchange The exchange
	 * @param authentication The {@link Authentication}
	 * @return A completion notification (success or error)
	 */
	@Override
	public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
		return Mono.fromRunnable(() -> exchange.getExchange().getResponse().setStatusCode(this.httpStatusToReturn));
	}

}
