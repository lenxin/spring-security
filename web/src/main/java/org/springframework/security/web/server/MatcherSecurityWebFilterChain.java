package org.springframework.security.web.server;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

/**
 * A {@link SecurityWebFilterChain} that leverages a {@link ServerWebExchangeMatcher} to
 * determine which {@link WebFilter} to execute.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class MatcherSecurityWebFilterChain implements SecurityWebFilterChain {

	private final ServerWebExchangeMatcher matcher;

	private final List<WebFilter> filters;

	public MatcherSecurityWebFilterChain(ServerWebExchangeMatcher matcher, List<WebFilter> filters) {
		Assert.notNull(matcher, "matcher cannot be null");
		Assert.notEmpty(filters, () -> "filters cannot be null or empty. Got " + filters);
		this.matcher = matcher;
		this.filters = filters;
	}

	@Override
	public Mono<Boolean> matches(ServerWebExchange exchange) {
		return this.matcher.matches(exchange).map((m) -> m.isMatch());
	}

	@Override
	public Flux<WebFilter> getWebFilters() {
		return Flux.fromIterable(this.filters);
	}

}
