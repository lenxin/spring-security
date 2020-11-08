package org.springframework.security.web.server.header;

import java.util.function.Supplier;

import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

/**
 * Interface for writing headers just before the response is committed.
 *
 * @author Rob Winch
 * @since 5.0
 */
public interface ServerHttpHeadersWriter {

	/**
	 * Write the headers to the response.
	 * @param exchange
	 * @return A Mono which is returned to the {@link Supplier} of the
	 * {@link ServerHttpResponse#beforeCommit(Supplier)}.
	 */
	Mono<Void> writeHttpHeaders(ServerWebExchange exchange);

}
