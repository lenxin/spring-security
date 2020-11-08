package org.springframework.security.web.server.authentication.logout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.util.Assert;

/**
 * Delegates to a collection of {@link ServerLogoutHandler} implementations.
 *
 * @author Eric Deandrea
 * @since 5.1
 */
public class DelegatingServerLogoutHandler implements ServerLogoutHandler {

	private final List<ServerLogoutHandler> delegates = new ArrayList<>();

	public DelegatingServerLogoutHandler(ServerLogoutHandler... delegates) {
		Assert.notEmpty(delegates, "delegates cannot be null or empty");
		this.delegates.addAll(Arrays.asList(delegates));
	}

	public DelegatingServerLogoutHandler(Collection<ServerLogoutHandler> delegates) {
		Assert.notEmpty(delegates, "delegates cannot be null or empty");
		this.delegates.addAll(delegates);
	}

	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		return Flux.fromIterable(this.delegates).concatMap((delegate) -> delegate.logout(exchange, authentication))
				.then();
	}

}
