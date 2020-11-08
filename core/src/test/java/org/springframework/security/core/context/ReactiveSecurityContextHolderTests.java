package org.springframework.security.core.context;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class ReactiveSecurityContextHolderTests {

	@Test
	public void getContextWhenEmpty() {
		Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
		// @formatter:off
		StepVerifier.create(context)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void setContextAndGetContextThenEmitsContext() {
		SecurityContext expectedContext = new SecurityContextImpl(
				new TestingAuthenticationToken("user", "password", "ROLE_USER"));
		Mono<SecurityContext> context = Mono.subscriberContext()
				.flatMap((c) -> ReactiveSecurityContextHolder.getContext())
				.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(expectedContext)));
		// @formatter:off
		StepVerifier.create(context)
				.expectNext(expectedContext)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void demo() {
		Authentication authentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
		// @formatter:off
		Mono<String> messageByUsername = ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.map(Authentication::getName)
				.flatMap(this::findMessageByUsername)
				.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
		StepVerifier.create(messageByUsername)
				.expectNext("Hi user")
				.verifyComplete();
		// @formatter:on
	}

	private Mono<String> findMessageByUsername(String username) {
		return Mono.just("Hi " + username);
	}

	@Test
	public void setContextAndClearAndGetContextThenEmitsEmpty() {
		SecurityContext expectedContext = new SecurityContextImpl(
				new TestingAuthenticationToken("user", "password", "ROLE_USER"));
		// @formatter:off
		Mono<SecurityContext> context = Mono.subscriberContext()
				.flatMap((c) -> ReactiveSecurityContextHolder.getContext())
				.subscriberContext(ReactiveSecurityContextHolder.clearContext())
				.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(expectedContext)));
		StepVerifier.create(context)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void setAuthenticationAndGetContextThenEmitsContext() {
		Authentication expectedAuthentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
		// @formatter:off
		Mono<Authentication> authentication = Mono.subscriberContext()
				.flatMap((c) -> ReactiveSecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(expectedAuthentication));
		StepVerifier.create(authentication)
				.expectNext(expectedAuthentication)
				.verifyComplete();
		// @formatter:on
	}

}
