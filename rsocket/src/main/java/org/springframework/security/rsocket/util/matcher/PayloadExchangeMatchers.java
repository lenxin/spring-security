package org.springframework.security.rsocket.util.matcher;

import reactor.core.publisher.Mono;

import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.api.PayloadExchangeType;

/**
 * @author Rob Winch
 */
public final class PayloadExchangeMatchers {

	private PayloadExchangeMatchers() {
	}

	public static PayloadExchangeMatcher setup() {
		return new PayloadExchangeMatcher() {

			@Override
			public Mono<MatchResult> matches(PayloadExchange exchange) {
				return PayloadExchangeType.SETUP.equals(exchange.getType()) ? MatchResult.match()
						: MatchResult.notMatch();
			}

		};
	}

	public static PayloadExchangeMatcher anyRequest() {
		return new PayloadExchangeMatcher() {

			@Override
			public Mono<MatchResult> matches(PayloadExchange exchange) {
				return exchange.getType().isRequest() ? MatchResult.match() : MatchResult.notMatch();
			}

		};
	}

	public static PayloadExchangeMatcher anyExchange() {
		return new PayloadExchangeMatcher() {

			@Override
			public Mono<MatchResult> matches(PayloadExchange exchange) {
				return MatchResult.match();
			}

		};
	}

}
