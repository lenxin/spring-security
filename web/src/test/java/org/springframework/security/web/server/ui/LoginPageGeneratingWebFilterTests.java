package org.springframework.security.web.server.ui;

import org.junit.Test;
import reactor.core.publisher.Mono;

import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPageGeneratingWebFilterTests {

	@Test
	public void filterWhenLoginWithContextPathThenActionContainsContextPath() throws Exception {
		LoginPageGeneratingWebFilter filter = new LoginPageGeneratingWebFilter();
		filter.setFormLoginEnabled(true);
		MockServerWebExchange exchange = MockServerWebExchange
				.from(MockServerHttpRequest.get("/test/login").contextPath("/test"));
		filter.filter(exchange, (e) -> Mono.empty()).block();
		assertThat(exchange.getResponse().getBodyAsString().block()).contains("action=\"/test/login\"");
	}

	@Test
	public void filterWhenLoginWithNoContextPathThenActionDoesNotContainsContextPath() throws Exception {
		LoginPageGeneratingWebFilter filter = new LoginPageGeneratingWebFilter();
		filter.setFormLoginEnabled(true);
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/login"));
		filter.filter(exchange, (e) -> Mono.empty()).block();
		assertThat(exchange.getResponse().getBodyAsString().block()).contains("action=\"/login\"");
	}

}
