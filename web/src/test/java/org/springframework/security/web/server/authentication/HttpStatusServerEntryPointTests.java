package org.springframework.security.web.server.authentication;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Eric Deandrea
 * @since 5.1
 */
public class HttpStatusServerEntryPointTests {

	private MockServerHttpRequest request;

	private MockServerWebExchange exchange;

	private AuthenticationException authException;

	private HttpStatusServerEntryPoint entryPoint;

	@Before
	public void setup() {
		this.request = MockServerHttpRequest.get("/").build();
		this.exchange = MockServerWebExchange.from(this.request);
		this.authException = new AuthenticationException("") {
		};
		this.entryPoint = new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void constructorNullStatus() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HttpStatusServerEntryPoint(null))
				.withMessage("httpStatus cannot be null");
	}

	@Test
	public void unauthorized() {
		this.entryPoint.commence(this.exchange, this.authException).block();
		assertThat(this.exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

}
