package org.springframework.security.web.server.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerAuthenticationEntryPointFailureHandlerTests {

	@Mock
	private ServerAuthenticationEntryPoint authenticationEntryPoint;

	@Mock
	private ServerWebExchange exchange;

	@Mock
	private WebFilterChain chain;

	@InjectMocks
	private WebFilterExchange filterExchange;

	@InjectMocks
	private ServerAuthenticationEntryPointFailureHandler handler;

	@Test
	public void constructorWhenNullEntryPointThenException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ServerAuthenticationEntryPointFailureHandler(null));
	}

	@Test
	public void onAuthenticationFailureWhenInvokedThenDelegatesToEntryPoint() {
		Mono<Void> result = Mono.empty();
		BadCredentialsException e = new BadCredentialsException("Failed");
		given(this.authenticationEntryPoint.commence(this.exchange, e)).willReturn(result);
		assertThat(this.handler.onAuthenticationFailure(this.filterExchange, e)).isEqualTo(result);
	}

}
