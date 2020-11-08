package org.springframework.security.rsocket.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.rsocket.api.PayloadExchange;

/**
 * Converts from a {@link PayloadExchange} to an {@link Authentication}
 *
 * @author Rob Winch
 * @since 5.2
 */
public interface PayloadExchangeAuthenticationConverter {

	Mono<Authentication> convert(PayloadExchange exchange);

}
