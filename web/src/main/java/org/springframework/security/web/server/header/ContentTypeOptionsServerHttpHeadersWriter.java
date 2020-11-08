package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;

/**
 * Adds X-Content-Type-Options: nosniff
 *
 * @author Rob Winch
 * @since 5.0
 */
public class ContentTypeOptionsServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	public static final String X_CONTENT_OPTIONS = "X-Content-Type-Options";

	public static final String NOSNIFF = "nosniff";

	/**
	 * The delegate to write all the cache control related headers
	 */
	private static final ServerHttpHeadersWriter CONTENT_TYPE_HEADERS = StaticServerHttpHeadersWriter.builder()
			.header(X_CONTENT_OPTIONS, NOSNIFF).build();

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		return CONTENT_TYPE_HEADERS.writeHttpHeaders(exchange);
	}

}
