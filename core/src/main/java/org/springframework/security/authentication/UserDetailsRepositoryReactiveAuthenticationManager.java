package org.springframework.security.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * A {@link ReactiveAuthenticationManager} that uses a {@link ReactiveUserDetailsService}
 * to validate the provided username and password.
 *
 * @author Rob Winch
 * @author Eddú Meléndez
 * @since 5.0
 */
public class UserDetailsRepositoryReactiveAuthenticationManager
		extends AbstractUserDetailsReactiveAuthenticationManager {

	private ReactiveUserDetailsService userDetailsService;

	public UserDetailsRepositoryReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
		Assert.notNull(userDetailsService, "userDetailsService cannot be null");
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected Mono<UserDetails> retrieveUser(String username) {
		return this.userDetailsService.findByUsername(username);
	}

}
