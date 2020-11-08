package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

/**
 * A strategy used for converting from a {@link ServerWebExchange} to an
 * {@link Authentication} used for authenticating with a provided
 * {@link org.springframework.security.authentication.ReactiveAuthenticationManager}. If
 * the result is {@link Mono#empty()}, then it signals that no authentication attempt
 * should be made.
 *
 * @author Eric Deandrea
 * @since 5.1
 */
@FunctionalInterface
public interface ServerAuthenticationConverter {

	/**
	 * Converts a {@link ServerWebExchange} to an {@link Authentication}
	 * @param exchange The {@link ServerWebExchange}
	 * @return A {@link Mono} representing an {@link Authentication}
	 */
	Mono<Authentication> convert(ServerWebExchange exchange);

}
