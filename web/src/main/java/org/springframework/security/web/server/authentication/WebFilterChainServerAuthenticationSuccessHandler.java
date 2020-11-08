package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.web.server.ServerWebExchange;

/**
 * Success handler that continues the filter chain after authentication success.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class WebFilterChainServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
		ServerWebExchange exchange = webFilterExchange.getExchange();
		return webFilterExchange.getChain().filter(exchange);
	}

}
