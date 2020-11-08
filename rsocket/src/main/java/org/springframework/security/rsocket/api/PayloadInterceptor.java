package org.springframework.security.rsocket.api;

import reactor.core.publisher.Mono;

/**
 * Contract for interception-style, chained processing of Payloads that may be used to
 * implement cross-cutting, application-agnostic requirements such as security, timeouts,
 * and others.
 *
 * @author Rob Winch
 * @since 5.2
 */
public interface PayloadInterceptor {

	/**
	 * Process the Web request and (optionally) delegate to the next
	 * {@code PayloadInterceptor} through the given {@link PayloadInterceptorChain}.
	 * @param exchange the current payload exchange
	 * @param chain provides a way to delegate to the next interceptor
	 * @return {@code Mono<Void>} to indicate when payload processing is complete
	 */
	Mono<Void> intercept(PayloadExchange exchange, PayloadInterceptorChain chain);

}
