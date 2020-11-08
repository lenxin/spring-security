package org.springframework.security.authentication;

import reactor.core.publisher.Mono;

/**
 * An interface for resolving a {@link ReactiveAuthenticationManager} based on the
 * provided context
 *
 * @author Rafiullah Hamedy
 * @since 5.2
 */
@FunctionalInterface
public interface ReactiveAuthenticationManagerResolver<C> {

	Mono<ReactiveAuthenticationManager> resolve(C context);

}
