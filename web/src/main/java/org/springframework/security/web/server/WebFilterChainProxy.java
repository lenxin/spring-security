package org.springframework.security.web.server;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.handler.DefaultWebFilterChain;
import org.springframework.web.server.handler.FilteringWebHandler;

/**
 * Used to delegate to a List of {@link SecurityWebFilterChain} instances.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class WebFilterChainProxy implements WebFilter {

	private final List<SecurityWebFilterChain> filters;

	public WebFilterChainProxy(List<SecurityWebFilterChain> filters) {
		this.filters = filters;
	}

	public WebFilterChainProxy(SecurityWebFilterChain... filters) {
		this.filters = Arrays.asList(filters);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return Flux.fromIterable(this.filters)
				.filterWhen((securityWebFilterChain) -> securityWebFilterChain.matches(exchange)).next()
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
				.flatMap((securityWebFilterChain) -> securityWebFilterChain.getWebFilters().collectList())
				.map((filters) -> new FilteringWebHandler(chain::filter, filters)).map(DefaultWebFilterChain::new)
				.flatMap((securedChain) -> securedChain.filter(exchange));
	}

}
