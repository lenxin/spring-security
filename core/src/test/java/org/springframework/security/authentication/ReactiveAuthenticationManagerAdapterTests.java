package org.springframework.security.authentication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ReactiveAuthenticationManagerAdapterTests {

	@Mock
	AuthenticationManager delegate;

	@Mock
	Authentication authentication;

	ReactiveAuthenticationManagerAdapter manager;

	@Before
	public void setup() {
		this.manager = new ReactiveAuthenticationManagerAdapter(this.delegate);
	}

	@Test
	public void constructorNullAuthenticationManager() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ReactiveAuthenticationManagerAdapter(null));
	}

	@Test
	public void setSchedulerNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.manager.setScheduler(null));
	}

	@Test
	public void authenticateWhenSuccessThenSuccess() {
		given(this.delegate.authenticate(any())).willReturn(this.authentication);
		given(this.authentication.isAuthenticated()).willReturn(true);
		Authentication result = this.manager.authenticate(this.authentication).block();
		assertThat(result).isEqualTo(this.authentication);
	}

	@Test
	public void authenticateWhenReturnNotAuthenticatedThenError() {
		given(this.delegate.authenticate(any())).willReturn(this.authentication);
		Authentication result = this.manager.authenticate(this.authentication).block();
		assertThat(result).isNull();
	}

	@Test
	public void authenticateWhenBadCredentialsThenError() {
		given(this.delegate.authenticate(any())).willThrow(new BadCredentialsException("Failed"));
		Mono<Authentication> result = this.manager.authenticate(this.authentication);
		// @formatter:off
		StepVerifier.create(result)
				.expectError(BadCredentialsException.class)
				.verify();
		// @formatter:on
	}

}
