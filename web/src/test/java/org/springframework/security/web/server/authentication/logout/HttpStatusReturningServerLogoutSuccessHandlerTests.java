package org.springframework.security.web.server.authentication.logout;

import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

/**
 * @author Eric Deandrea
 * @since 5.1
 */
public class HttpStatusReturningServerLogoutSuccessHandlerTests {

	@Test
	public void defaultHttpStatusBeingReturned() {
		WebFilterExchange filterExchange = buildFilterExchange();
		new HttpStatusReturningServerLogoutSuccessHandler().onLogoutSuccess(filterExchange, mock(Authentication.class))
				.block();
		assertThat(filterExchange.getExchange().getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void customHttpStatusBeingReturned() {
		WebFilterExchange filterExchange = buildFilterExchange();
		new HttpStatusReturningServerLogoutSuccessHandler(HttpStatus.NO_CONTENT)
				.onLogoutSuccess(filterExchange, mock(Authentication.class)).block();
		assertThat(filterExchange.getExchange().getResponse().getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void nullHttpStatusThrowsException() {
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new HttpStatusReturningServerLogoutSuccessHandler(null))
				.withMessage("The provided HttpStatus must not be null.");
	}

	private static WebFilterExchange buildFilterExchange() {
		MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
		MockServerWebExchange exchange = MockServerWebExchange.from(request);
		return new WebFilterExchange(exchange, mock(WebFilterChain.class));
	}

}
