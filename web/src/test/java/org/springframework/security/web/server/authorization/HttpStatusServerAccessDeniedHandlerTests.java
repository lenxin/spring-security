package org.springframework.security.web.server.authorization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpStatusServerAccessDeniedHandlerTests {

	@Mock
	private ServerWebExchange exchange;

	private HttpStatus httpStatus = HttpStatus.FORBIDDEN;

	private HttpStatusServerAccessDeniedHandler handler = new HttpStatusServerAccessDeniedHandler(this.httpStatus);

	private AccessDeniedException exception = new AccessDeniedException("Forbidden");

	@Test
	public void constructorHttpStatusWhenNullThenException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new HttpStatusServerAccessDeniedHandler(null));
	}

	@Test
	public void commenceWhenNoSubscribersThenNoActions() {
		this.handler.handle(this.exchange, this.exception);
		verifyZeroInteractions(this.exchange);
	}

	@Test
	public void commenceWhenSubscribeThenStatusSet() {
		this.exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
		this.handler.handle(this.exchange, this.exception).block();
		assertThat(this.exchange.getResponse().getStatusCode()).isEqualTo(this.httpStatus);
	}

	@Test
	public void commenceWhenCustomStatusSubscribeThenStatusSet() {
		this.httpStatus = HttpStatus.NOT_FOUND;
		this.handler = new HttpStatusServerAccessDeniedHandler(this.httpStatus);
		this.exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
		this.handler.handle(this.exchange, this.exception).block();
		assertThat(this.exchange.getResponse().getStatusCode()).isEqualTo(this.httpStatus);
	}

}
