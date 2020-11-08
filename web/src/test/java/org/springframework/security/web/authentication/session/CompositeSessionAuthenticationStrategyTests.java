package org.springframework.security.web.authentication.session;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CompositeSessionAuthenticationStrategyTests {

	@Mock
	private SessionAuthenticationStrategy strategy1;

	@Mock
	private SessionAuthenticationStrategy strategy2;

	@Mock
	private Authentication authentication;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Test
	public void constructorNullDelegates() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CompositeSessionAuthenticationStrategy(null));
	}

	@Test
	public void constructorEmptyDelegates() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CompositeSessionAuthenticationStrategy(
				Collections.<SessionAuthenticationStrategy>emptyList()));
	}

	@Test
	public void constructorDelegatesContainNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CompositeSessionAuthenticationStrategy(
				Collections.<SessionAuthenticationStrategy>singletonList(null)));
	}

	@Test
	public void delegatesToAll() {
		CompositeSessionAuthenticationStrategy strategy = new CompositeSessionAuthenticationStrategy(
				Arrays.asList(this.strategy1, this.strategy2));
		strategy.onAuthentication(this.authentication, this.request, this.response);
		verify(this.strategy1).onAuthentication(this.authentication, this.request, this.response);
		verify(this.strategy2).onAuthentication(this.authentication, this.request, this.response);
	}

	@Test
	public void delegateShortCircuits() {
		willThrow(new SessionAuthenticationException("oops")).given(this.strategy1)
				.onAuthentication(this.authentication, this.request, this.response);
		CompositeSessionAuthenticationStrategy strategy = new CompositeSessionAuthenticationStrategy(
				Arrays.asList(this.strategy1, this.strategy2));
		assertThatExceptionOfType(SessionAuthenticationException.class)
				.isThrownBy(() -> strategy.onAuthentication(this.authentication, this.request, this.response));
		verify(this.strategy1).onAuthentication(this.authentication, this.request, this.response);
		verify(this.strategy2, times(0)).onAuthentication(this.authentication, this.request, this.response);
	}

}
