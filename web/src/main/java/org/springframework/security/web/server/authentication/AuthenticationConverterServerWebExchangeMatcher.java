package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Matches if the {@link ServerAuthenticationConverter} can convert a
 * {@link ServerWebExchange} to an {@link Authentication}.
 *
 * @author David Kovac
 * @since 5.4
 * @see ServerAuthenticationConverter
 */
public final class AuthenticationConverterServerWebExchangeMatcher implements ServerWebExchangeMatcher {

	private final ServerAuthenticationConverter serverAuthenticationConverter;

	public AuthenticationConverterServerWebExchangeMatcher(
			ServerAuthenticationConverter serverAuthenticationConverter) {
		Assert.notNull(serverAuthenticationConverter, "serverAuthenticationConverter cannot be null");
		this.serverAuthenticationConverter = serverAuthenticationConverter;
	}

	@Override
	public Mono<MatchResult> matches(ServerWebExchange exchange) {
		return this.serverAuthenticationConverter.convert(exchange).flatMap((a) -> MatchResult.match())
				.onErrorResume((ex) -> MatchResult.notMatch()).switchIfEmpty(MatchResult.notMatch());
	}

}
