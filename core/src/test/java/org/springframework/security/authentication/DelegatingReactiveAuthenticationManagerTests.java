package org.springframework.security.authentication;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 * @since 5.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DelegatingReactiveAuthenticationManagerTests {

	@Mock
	ReactiveAuthenticationManager delegate1;

	@Mock
	ReactiveAuthenticationManager delegate2;

	@Mock
	Authentication authentication;

	@Test
	public void authenticateWhenEmptyAndNotThenReturnsNotEmpty() {
		given(this.delegate1.authenticate(any())).willReturn(Mono.empty());
		given(this.delegate2.authenticate(any())).willReturn(Mono.just(this.authentication));
		DelegatingReactiveAuthenticationManager manager = new DelegatingReactiveAuthenticationManager(this.delegate1,
				this.delegate2);
		assertThat(manager.authenticate(this.authentication).block()).isEqualTo(this.authentication);
	}

	@Test
	public void authenticateWhenNotEmptyThenOtherDelegatesNotSubscribed() {
		// delay to try and force delegate2 to finish (i.e. make sure we didn't use
		// flatMap)
		given(this.delegate1.authenticate(any()))
				.willReturn(Mono.just(this.authentication).delayElement(Duration.ofMillis(100)));
		DelegatingReactiveAuthenticationManager manager = new DelegatingReactiveAuthenticationManager(this.delegate1,
				this.delegate2);
		StepVerifier.create(manager.authenticate(this.authentication)).expectNext(this.authentication).verifyComplete();
	}

	@Test
	public void authenticateWhenBadCredentialsThenDelegate2NotInvokedAndError() {
		given(this.delegate1.authenticate(any())).willReturn(Mono.error(new BadCredentialsException("Test")));
		DelegatingReactiveAuthenticationManager manager = new DelegatingReactiveAuthenticationManager(this.delegate1,
				this.delegate2);
		StepVerifier.create(manager.authenticate(this.authentication)).expectError(BadCredentialsException.class)
				.verify();
	}

}
