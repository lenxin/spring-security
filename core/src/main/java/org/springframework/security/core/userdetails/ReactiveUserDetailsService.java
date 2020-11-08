package org.springframework.security.core.userdetails;

import reactor.core.publisher.Mono;

/**
 * An API for finding the {@link UserDetails} by username.
 *
 * @author Rob Winch
 * @since 5.0
 */
public interface ReactiveUserDetailsService {

	/**
	 * Find the {@link UserDetails} by username.
	 * @param username the username to look up
	 * @return the {@link UserDetails}. Cannot be null
	 */
	Mono<UserDetails> findByUsername(String username);

}
