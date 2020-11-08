package org.springframework.security.web.server.authentication;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.util.Assert;

/**
 * Delegates to a collection of {@link ServerAuthenticationSuccessHandler}
 * implementations.
 *
 * @author Rob Winch
 * @since 5.1
 */
public class DelegatingServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

	private final List<ServerAuthenticationSuccessHandler> delegates;

	public DelegatingServerAuthenticationSuccessHandler(ServerAuthenticationSuccessHandler... delegates) {
		Assert.notEmpty(delegates, "delegates cannot be null or empty");
		this.delegates = Arrays.asList(delegates);
	}

	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
		return Flux.fromIterable(this.delegates)
				.concatMap((delegate) -> delegate.onAuthenticationSuccess(exchange, authentication)).then();
	}

}
