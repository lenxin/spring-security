package org.springframework.security.rsocket.authorization;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeAuthorizationContext;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeMatcher;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeMatcher.MatchResult;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeMatcherEntry;
import org.springframework.util.Assert;

/**
 * Maps a @{code List} of {@link PayloadExchangeMatcher} instances to
 *
 * @{code ReactiveAuthorizationManager} instances.
 * @author Rob Winch
 * @since 5.2
 */
public final class PayloadExchangeMatcherReactiveAuthorizationManager
		implements ReactiveAuthorizationManager<PayloadExchange> {

	private final List<PayloadExchangeMatcherEntry<ReactiveAuthorizationManager<PayloadExchangeAuthorizationContext>>> mappings;

	private PayloadExchangeMatcherReactiveAuthorizationManager(
			List<PayloadExchangeMatcherEntry<ReactiveAuthorizationManager<PayloadExchangeAuthorizationContext>>> mappings) {
		Assert.notEmpty(mappings, "mappings cannot be null");
		this.mappings = mappings;
	}

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, PayloadExchange exchange) {
		return Flux.fromIterable(this.mappings)
				.concatMap((mapping) -> mapping.getMatcher().matches(exchange)
						.filter(PayloadExchangeMatcher.MatchResult::isMatch).map(MatchResult::getVariables)
						.flatMap((variables) -> mapping.getEntry().check(authentication,
								new PayloadExchangeAuthorizationContext(exchange, variables))))
				.next().switchIfEmpty(Mono.fromCallable(() -> new AuthorizationDecision(false)));
	}

	public static PayloadExchangeMatcherReactiveAuthorizationManager.Builder builder() {
		return new PayloadExchangeMatcherReactiveAuthorizationManager.Builder();
	}

	public static final class Builder {

		private final List<PayloadExchangeMatcherEntry<ReactiveAuthorizationManager<PayloadExchangeAuthorizationContext>>> mappings = new ArrayList<>();

		private Builder() {
		}

		public PayloadExchangeMatcherReactiveAuthorizationManager.Builder add(
				PayloadExchangeMatcherEntry<ReactiveAuthorizationManager<PayloadExchangeAuthorizationContext>> entry) {
			this.mappings.add(entry);
			return this;
		}

		public PayloadExchangeMatcherReactiveAuthorizationManager build() {
			return new PayloadExchangeMatcherReactiveAuthorizationManager(this.mappings);
		}

	}

}
