package org.springframework.security.authorization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticatedReactiveAuthorizationManagerTests {

	@Mock
	Authentication authentication;

	AuthenticatedReactiveAuthorizationManager<Object> manager = AuthenticatedReactiveAuthorizationManager
			.authenticated();

	@Test
	public void checkWhenAuthenticatedThenReturnTrue() {
		given(this.authentication.isAuthenticated()).willReturn(true);
		boolean granted = this.manager.check(Mono.just(this.authentication), null).block().isGranted();
		assertThat(granted).isTrue();
	}

	@Test
	public void checkWhenNotAuthenticatedThenReturnFalse() {
		boolean granted = this.manager.check(Mono.just(this.authentication), null).block().isGranted();
		assertThat(granted).isFalse();
	}

	@Test
	public void checkWhenEmptyThenReturnFalse() {
		boolean granted = this.manager.check(Mono.empty(), null).block().isGranted();
		assertThat(granted).isFalse();
	}

	@Test
	public void checkWhenAnonymousAuthenticatedThenReturnFalse() {
		AnonymousAuthenticationToken anonymousAuthenticationToken = mock(AnonymousAuthenticationToken.class);
		boolean granted = this.manager.check(Mono.just(anonymousAuthenticationToken), null).block().isGranted();
		assertThat(granted).isFalse();
	}

	@Test
	public void checkWhenErrorThenError() {
		Mono<AuthorizationDecision> result = this.manager.check(Mono.error(new RuntimeException("ooops")), null);
		// @formatter:off
		StepVerifier.create(result)
				.expectError()
				.verify();
		// @formatter:on
	}

}
