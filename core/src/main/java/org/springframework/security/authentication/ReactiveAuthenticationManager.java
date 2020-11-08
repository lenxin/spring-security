package org.springframework.security.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;

/**
 * Determines if the provided {@link Authentication} can be authenticated.
 *
 * @author Rob Winch
 * @since 5.0
 */
@FunctionalInterface
public interface ReactiveAuthenticationManager {

	/**
	 * Attempts to authenticate the provided {@link Authentication}
	 * @param authentication the {@link Authentication} to test
	 * @return if authentication is successful an {@link Authentication} is returned. If
	 * authentication cannot be determined, an empty Mono is returned. If authentication
	 * fails, a Mono error is returned.
	 */
	Mono<Authentication> authenticate(Authentication authentication);

}
