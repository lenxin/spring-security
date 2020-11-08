package org.springframework.security.rsocket.authorization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeAuthorizationContext;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeMatcher;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeMatcherEntry;
import org.springframework.security.rsocket.util.matcher.PayloadExchangeMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 */
@RunWith(MockitoJUnitRunner.class)
public class PayloadExchangeMatcherReactiveAuthorizationManagerTests {

	@Mock
	private ReactiveAuthorizationManager<PayloadExchangeAuthorizationContext> authz;

	@Mock
	private ReactiveAuthorizationManager<PayloadExchangeAuthorizationContext> authz2;

	@Mock
	private PayloadExchange exchange;

	@Test
	public void checkWhenGrantedThenGranted() {
		AuthorizationDecision expected = new AuthorizationDecision(true);
		given(this.authz.check(any(), any())).willReturn(Mono.just(expected));
		PayloadExchangeMatcherReactiveAuthorizationManager manager = PayloadExchangeMatcherReactiveAuthorizationManager
				.builder().add(new PayloadExchangeMatcherEntry<>(PayloadExchangeMatchers.anyExchange(), this.authz))
				.build();
		assertThat(manager.check(Mono.empty(), this.exchange).block()).isEqualTo(expected);
	}

	@Test
	public void checkWhenDeniedThenDenied() {
		AuthorizationDecision expected = new AuthorizationDecision(false);
		given(this.authz.check(any(), any())).willReturn(Mono.just(expected));
		PayloadExchangeMatcherReactiveAuthorizationManager manager = PayloadExchangeMatcherReactiveAuthorizationManager
				.builder().add(new PayloadExchangeMatcherEntry<>(PayloadExchangeMatchers.anyExchange(), this.authz))
				.build();
		assertThat(manager.check(Mono.empty(), this.exchange).block()).isEqualTo(expected);
	}

	@Test
	public void checkWhenFirstMatchThenSecondUsed() {
		AuthorizationDecision expected = new AuthorizationDecision(true);
		given(this.authz.check(any(), any())).willReturn(Mono.just(expected));
		PayloadExchangeMatcherReactiveAuthorizationManager manager = PayloadExchangeMatcherReactiveAuthorizationManager
				.builder().add(new PayloadExchangeMatcherEntry<>(PayloadExchangeMatchers.anyExchange(), this.authz))
				.add(new PayloadExchangeMatcherEntry<>((e) -> PayloadExchangeMatcher.MatchResult.notMatch(),
						this.authz2))
				.build();
		assertThat(manager.check(Mono.empty(), this.exchange).block()).isEqualTo(expected);
	}

	@Test
	public void checkWhenSecondMatchThenSecondUsed() {
		AuthorizationDecision expected = new AuthorizationDecision(true);
		given(this.authz2.check(any(), any())).willReturn(Mono.just(expected));
		PayloadExchangeMatcherReactiveAuthorizationManager manager = PayloadExchangeMatcherReactiveAuthorizationManager
				.builder()
				.add(new PayloadExchangeMatcherEntry<>((e) -> PayloadExchangeMatcher.MatchResult.notMatch(),
						this.authz))
				.add(new PayloadExchangeMatcherEntry<>(PayloadExchangeMatchers.anyExchange(), this.authz2)).build();
		assertThat(manager.check(Mono.empty(), this.exchange).block()).isEqualTo(expected);
	}

}
