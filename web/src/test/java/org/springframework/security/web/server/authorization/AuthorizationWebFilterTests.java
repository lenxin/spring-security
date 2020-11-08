package org.springframework.security.web.server.authorization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorizationWebFilterTests {

	@Mock
	private ServerWebExchange exchange;

	@Mock
	private WebFilterChain chain;

	PublisherProbe<Void> chainResult = PublisherProbe.empty();

	@Test
	public void filterWhenNoSecurityContextThenThrowsAccessDenied() {
		given(this.chain.filter(this.exchange)).willReturn(this.chainResult.mono());
		AuthorizationWebFilter filter = new AuthorizationWebFilter(
				(a, e) -> Mono.error(new AccessDeniedException("Denied")));
		Mono<Void> result = filter.filter(this.exchange, this.chain);
		StepVerifier.create(result).expectError(AccessDeniedException.class).verify();
		this.chainResult.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenNoAuthenticationThenThrowsAccessDenied() {
		given(this.chain.filter(this.exchange)).willReturn(this.chainResult.mono());
		AuthorizationWebFilter filter = new AuthorizationWebFilter(
				(a, e) -> a.flatMap((auth) -> Mono.error(new AccessDeniedException("Denied"))));
		Mono<Void> result = filter.filter(this.exchange, this.chain).subscriberContext(
				ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl())));
		StepVerifier.create(result).expectError(AccessDeniedException.class).verify();
		this.chainResult.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenAuthenticationThenThrowsAccessDenied() {
		given(this.chain.filter(this.exchange)).willReturn(this.chainResult.mono());
		AuthorizationWebFilter filter = new AuthorizationWebFilter(
				(a, e) -> Mono.error(new AccessDeniedException("Denied")));
		Mono<Void> result = filter.filter(this.exchange, this.chain).subscriberContext(
				ReactiveSecurityContextHolder.withAuthentication(new TestingAuthenticationToken("a", "b", "R")));
		StepVerifier.create(result).expectError(AccessDeniedException.class).verify();
		this.chainResult.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenDoesNotAccessAuthenticationThenSecurityContextNotSubscribed() {
		PublisherProbe<SecurityContext> context = PublisherProbe.empty();
		given(this.chain.filter(this.exchange)).willReturn(this.chainResult.mono());
		AuthorizationWebFilter filter = new AuthorizationWebFilter(
				(a, e) -> Mono.error(new AccessDeniedException("Denied")));
		Mono<Void> result = filter.filter(this.exchange, this.chain)
				.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(context.mono()));
		StepVerifier.create(result).expectError(AccessDeniedException.class).verify();
		this.chainResult.assertWasNotSubscribed();
		context.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenGrantedAndDoesNotAccessAuthenticationThenChainSubscribedAndSecurityContextNotSubscribed() {
		PublisherProbe<SecurityContext> context = PublisherProbe.empty();
		given(this.chain.filter(this.exchange)).willReturn(this.chainResult.mono());
		AuthorizationWebFilter filter = new AuthorizationWebFilter(
				(a, e) -> Mono.just(new AuthorizationDecision(true)));
		Mono<Void> result = filter.filter(this.exchange, this.chain)
				.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(context.mono()));
		StepVerifier.create(result).verifyComplete();
		this.chainResult.assertWasSubscribed();
		context.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenGrantedAndDoeAccessAuthenticationThenChainSubscribedAndSecurityContextSubscribed() {
		PublisherProbe<SecurityContext> context = PublisherProbe.empty();
		given(this.chain.filter(this.exchange)).willReturn(this.chainResult.mono());
		AuthorizationWebFilter filter = new AuthorizationWebFilter((a, e) -> a
				.map((auth) -> new AuthorizationDecision(true)).defaultIfEmpty(new AuthorizationDecision(true)));
		Mono<Void> result = filter.filter(this.exchange, this.chain)
				.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(context.mono()));
		StepVerifier.create(result).verifyComplete();
		this.chainResult.assertWasSubscribed();
		context.assertWasSubscribed();
	}

}
