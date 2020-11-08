package org.springframework.security.web.server.savedrequest;

import reactor.core.publisher.Mono;

import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * A {@link WebFilter} that replays any matching request in {@link ServerRequestCache}
 *
 * @author Rob Winch
 * @since 5.0
 */
public class ServerRequestCacheWebFilter implements WebFilter {

	private ServerRequestCache requestCache = new WebSessionServerRequestCache();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return this.requestCache.removeMatchingRequest(exchange).map((r) -> exchange.mutate().request(r).build())
				.defaultIfEmpty(exchange).flatMap((e) -> chain.filter(e));
	}

	public void setRequestCache(ServerRequestCache requestCache) {
		Assert.notNull(requestCache, "requestCache cannot be null");
		this.requestCache = requestCache;
	}

}
