package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

/**
 * Writes cache control related headers.
 *
 * <pre>
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 * </pre>
 *
 * @author Rob Winch
 * @since 5.0
 */
public class CacheControlServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	/**
	 * The value for expires value
	 */
	public static final String EXPIRES_VALUE = "0";

	/**
	 * The value for pragma value
	 */
	public static final String PRAGMA_VALUE = "no-cache";

	/**
	 * The value for cache control value
	 */
	public static final String CACHE_CONTRTOL_VALUE = "no-cache, no-store, max-age=0, must-revalidate";

	/**
	 * The delegate to write all the cache control related headers
	 */
	private static final ServerHttpHeadersWriter CACHE_HEADERS = StaticServerHttpHeadersWriter.builder()
			.header(HttpHeaders.CACHE_CONTROL, CacheControlServerHttpHeadersWriter.CACHE_CONTRTOL_VALUE)
			.header(HttpHeaders.PRAGMA, CacheControlServerHttpHeadersWriter.PRAGMA_VALUE)
			.header(HttpHeaders.EXPIRES, CacheControlServerHttpHeadersWriter.EXPIRES_VALUE).build();

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		if (exchange.getResponse().getStatusCode() == HttpStatus.NOT_MODIFIED) {
			return Mono.empty();
		}
		return CACHE_HEADERS.writeHttpHeaders(exchange);
	}

}
