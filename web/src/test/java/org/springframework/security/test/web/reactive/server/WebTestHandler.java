package org.springframework.security.test.web.reactive.server;

import java.util.Arrays;

import reactor.core.publisher.Mono;

import org.springframework.mock.http.server.reactive.MockServerHttpRequest.BaseBuilder;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.handler.FilteringWebHandler;

/**
 * @author Rob Winch
 * @since 5.0
 */
public final class WebTestHandler {

	private final MockWebHandler webHandler = new MockWebHandler();

	private final WebHandler handler;

	private WebTestHandler(WebFilter... filters) {
		this.handler = new FilteringWebHandler(this.webHandler, Arrays.asList(filters));
	}

	public WebHandlerResult exchange(BaseBuilder<?> baseBuilder) {
		ServerWebExchange exchange = MockServerWebExchange.from(baseBuilder.build());
		return exchange(exchange);
	}

	public WebHandlerResult exchange(ServerWebExchange exchange) {
		this.handler.handle(exchange).block();
		return new WebHandlerResult(this.webHandler.exchange);
	}

	public static WebTestHandler bindToWebFilters(WebFilter... filters) {
		return new WebTestHandler(filters);
	}

	public static final class WebHandlerResult {

		private final ServerWebExchange exchange;

		private WebHandlerResult(ServerWebExchange exchange) {
			this.exchange = exchange;
		}

		public ServerWebExchange getExchange() {
			return this.exchange;
		}

	}

	static class MockWebHandler implements WebHandler {

		private ServerWebExchange exchange;

		@Override
		public Mono<Void> handle(ServerWebExchange exchange) {
			this.exchange = exchange;
			return Mono.empty();
		}

	}

}
