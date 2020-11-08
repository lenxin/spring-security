package org.springframework.security.web.server;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

/**
 * Defines a filter chain which is capable of being matched against a
 * {@link ServerWebExchange} in order to decide whether it applies to that request.
 *
 * @author Rob Winch
 * @since 5.0
 */
public interface SecurityWebFilterChain {

	/**
	 * Determines if this {@link SecurityWebFilterChain} matches the provided
	 * {@link ServerWebExchange}
	 * @param exchange the {@link ServerWebExchange}
	 * @return true if it matches, else false
	 */
	Mono<Boolean> matches(ServerWebExchange exchange);

	/**
	 * The {@link WebFilter} to use
	 * @return
	 */
	Flux<WebFilter> getWebFilters();

}
