package org.springframework.security.web.server.savedrequest;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * An implementation of {@link ServerRequestCache} that does nothing. This is used in
 * stateless applications
 *
 * @author Rob Winch
 * @since 5.0
 */
public final class NoOpServerRequestCache implements ServerRequestCache {

	private NoOpServerRequestCache() {
	}

	@Override
	public Mono<Void> saveRequest(ServerWebExchange exchange) {
		return Mono.empty();
	}

	@Override
	public Mono<URI> getRedirectUri(ServerWebExchange exchange) {
		return Mono.empty();
	}

	@Override
	public Mono<ServerHttpRequest> removeMatchingRequest(ServerWebExchange exchange) {
		return Mono.empty();
	}

	public static NoOpServerRequestCache getInstance() {
		return new NoOpServerRequestCache();
	}

}
