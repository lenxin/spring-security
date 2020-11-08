package org.springframework.security.web.server.context;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Uses a {@link ServerSecurityContextRepository} to provide the {@link SecurityContext}
 * to initialize the {@link ReactiveSecurityContextHolder}.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class ReactorContextWebFilter implements WebFilter {

	private final ServerSecurityContextRepository repository;

	public ReactorContextWebFilter(ServerSecurityContextRepository repository) {
		Assert.notNull(repository, "repository cannot be null");
		this.repository = repository;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter(exchange).subscriberContext(
				(context) -> context.hasKey(SecurityContext.class) ? context : withSecurityContext(context, exchange));
	}

	private Context withSecurityContext(Context mainContext, ServerWebExchange exchange) {
		return mainContext
				.putAll(this.repository.load(exchange).as(ReactiveSecurityContextHolder::withSecurityContext));
	}

}
