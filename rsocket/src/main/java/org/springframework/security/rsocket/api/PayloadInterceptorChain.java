package org.springframework.security.rsocket.api;

import reactor.core.publisher.Mono;

/**
 * Contract to allow a {@link PayloadInterceptor} to delegate to the next in the chain. *
 *
 * @author Rob Winch
 * @since 5.2
 */
public interface PayloadInterceptorChain {

	/**
	 * Process the payload exchange.
	 * @param exchange the current server exchange
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	Mono<Void> next(PayloadExchange exchange);

}
