package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * A {@link ServerAuthenticationEntryPoint} that sends a generic {@link HttpStatus} as a
 * response. Useful for JavaScript clients which cannot use Basic authentication since the
 * browser intercepts the response.
 *
 * @author Eric Deandrea
 * @since 5.1
 */
public class HttpStatusServerEntryPoint implements ServerAuthenticationEntryPoint {

	private final HttpStatus httpStatus;

	public HttpStatusServerEntryPoint(HttpStatus httpStatus) {
		Assert.notNull(httpStatus, "httpStatus cannot be null");
		this.httpStatus = httpStatus;
	}

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
		return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(this.httpStatus));
	}

}
