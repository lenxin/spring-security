package org.springframework.security.web.server;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;

/**
 * A strategy for performing redirects.
 *
 * @author Rob Winch
 * @since 5.0
 */
@FunctionalInterface
public interface ServerRedirectStrategy {

	/**
	 * Performs a redirect based upon the provided {@link ServerWebExchange} and
	 * {@link URI}
	 * @param exchange the {@link ServerWebExchange} to use
	 * @param location the location to redirect to
	 * @return {@code Mono<Void>} to indicate when redirect is complete
	 */
	Mono<Void> sendRedirect(ServerWebExchange exchange, URI location);

}
