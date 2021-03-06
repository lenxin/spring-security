package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

/**
 * Converts a ServerWebExchange into a UsernamePasswordAuthenticationToken from the form
 * data HTTP parameters.
 *
 * @author Rob Winch
 * @since 5.1
 */
@SuppressWarnings("deprecation")
public class ServerFormLoginAuthenticationConverter
		extends org.springframework.security.web.server.ServerFormLoginAuthenticationConverter
		implements ServerAuthenticationConverter {

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		return apply(exchange);
	}

}
