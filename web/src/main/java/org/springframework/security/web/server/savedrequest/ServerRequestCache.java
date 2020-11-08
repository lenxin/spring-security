package org.springframework.security.web.server.savedrequest;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * Saves a {@link ServerHttpRequest} so it can be "replayed" later. This is useful for
 * when a page was requested and authentication is necessary.
 *
 * @author Rob Winch
 * @since 5.0
 */
public interface ServerRequestCache {

	/**
	 * Save the {@link ServerHttpRequest}
	 * @param exchange the exchange to save
	 * @return Return a {@code Mono<Void>} which only replays complete and error signals
	 * from this {@link Mono}.
	 */
	Mono<Void> saveRequest(ServerWebExchange exchange);

	/**
	 * Get the URI that can be redirected to trigger the saved request to be used
	 * @param exchange the exchange to obtain the saved {@link ServerHttpRequest} from
	 * @return the URI that can be redirected to trigger the saved request to be used
	 */
	Mono<URI> getRedirectUri(ServerWebExchange exchange);

	/**
	 * If the provided {@link ServerWebExchange} matches the saved
	 * {@link ServerHttpRequest} gets the saved {@link ServerHttpRequest}
	 * @param exchange the exchange to obtain the request from
	 * @return the {@link ServerHttpRequest}
	 */
	Mono<ServerHttpRequest> removeMatchingRequest(ServerWebExchange exchange);

}
