package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Invokes a {@link ServerHttpHeadersWriter} on
 * {@link ServerHttpResponse#beforeCommit(java.util.function.Supplier)}.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class HttpHeaderWriterWebFilter implements WebFilter {

	private final ServerHttpHeadersWriter writer;

	public HttpHeaderWriterWebFilter(ServerHttpHeadersWriter writer) {
		this.writer = writer;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		exchange.getResponse().beforeCommit(() -> this.writer.writeHttpHeaders(exchange));
		return chain.filter(exchange);
	}

}
