package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

/**
 * Converts from a {@link ServerWebExchange} to an {@link Authentication} that can be
 * authenticated.
 *
 * @author Rob Winch
 * @since 5.1
 */
@SuppressWarnings("deprecation")
public class ServerHttpBasicAuthenticationConverter
		extends org.springframework.security.web.server.ServerHttpBasicAuthenticationConverter
		implements ServerAuthenticationConverter {

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		return apply(exchange);
	}

}
