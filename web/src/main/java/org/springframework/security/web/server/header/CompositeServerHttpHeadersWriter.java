package org.springframework.security.web.server.header;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;

/**
 * Combines multiple {@link ServerHttpHeadersWriter} instances into a single instance.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class CompositeServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	private final List<ServerHttpHeadersWriter> writers;

	public CompositeServerHttpHeadersWriter(ServerHttpHeadersWriter... writers) {
		this(Arrays.asList(writers));
	}

	public CompositeServerHttpHeadersWriter(List<ServerHttpHeadersWriter> writers) {
		this.writers = writers;
	}

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		return Flux.fromIterable(this.writers).concatMap((w) -> w.writeHttpHeaders(exchange)).then();
	}

}
