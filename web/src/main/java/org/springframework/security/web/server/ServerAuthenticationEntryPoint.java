package org.springframework.security.web.server;

import reactor.core.publisher.Mono;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;

/**
 * Used to request authentication
 *
 * @author Rob Winch
 * @since 5.0
 */
@FunctionalInterface
public interface ServerAuthenticationEntryPoint {

	/**
	 * Initiates the authentication flow
	 * @param exchange
	 * @param ex
	 * @return {@code Mono<Void>} to indicate when the request for authentication is
	 * complete
	 */
	Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex);

}
