package org.springframework.security.authentication;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * A {@link ReactiveAuthenticationManager} that delegates to other
 * {@link ReactiveAuthenticationManager} instances using the result from the first non
 * empty result.
 *
 * @author Rob Winch
 * @since 5.1
 */
public class DelegatingReactiveAuthenticationManager implements ReactiveAuthenticationManager {

	private final List<ReactiveAuthenticationManager> delegates;

	public DelegatingReactiveAuthenticationManager(ReactiveAuthenticationManager... entryPoints) {
		this(Arrays.asList(entryPoints));
	}

	public DelegatingReactiveAuthenticationManager(List<ReactiveAuthenticationManager> entryPoints) {
		Assert.notEmpty(entryPoints, "entryPoints cannot be null");
		this.delegates = entryPoints;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		// @formatter:off
		return Flux.fromIterable(this.delegates)
				.concatMap((m) -> m.authenticate(authentication))
				.next();
		// @formatter:on
	}

}
