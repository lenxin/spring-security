package org.springframework.security.web.server.context;

import reactor.core.publisher.Mono;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Override the {@link ServerWebExchange#getPrincipal()} to be looked up using
 * {@link ReactiveSecurityContextHolder}.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class SecurityContextServerWebExchangeWebFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

		return chain.filter(new SecurityContextServerWebExchange(exchange, ReactiveSecurityContextHolder.getContext()));
	}

}
