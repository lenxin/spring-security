package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * <p>
 * Writes the {@code Clear-Site-Data} response header when the request is secure.
 * </p>
 *
 * <p>
 * For further details pleaes consult <a href="https://www.w3.org/TR/clear-site-data/">W3C
 * Documentation</a>.
 * </p>
 *
 * @author MD Sayem Ahmed
 * @since 5.2
 */
public final class ClearSiteDataServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	public static final String CLEAR_SITE_DATA_HEADER = "Clear-Site-Data";

	private final StaticServerHttpHeadersWriter headerWriterDelegate;

	/**
	 * <p>
	 * Constructs a new instance using the given directives.
	 * </p>
	 * @param directives directives that will be written as the header value
	 * @throws IllegalArgumentException if the argument is null or empty
	 */
	public ClearSiteDataServerHttpHeadersWriter(Directive... directives) {
		Assert.notEmpty(directives, "directives cannot be empty or null");
		this.headerWriterDelegate = StaticServerHttpHeadersWriter.builder()
				.header(CLEAR_SITE_DATA_HEADER, transformToHeaderValue(directives)).build();
	}

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		if (isSecure(exchange)) {
			return this.headerWriterDelegate.writeHttpHeaders(exchange);
		}
		return Mono.empty();
	}

	/**
	 * <p>
	 * Represents the directive values expected by the
	 * {@link ClearSiteDataServerHttpHeadersWriter}
	 * </p>
	 * .
	 */
	public enum Directive {

		CACHE("cache"),

		COOKIES("cookies"),

		STORAGE("storage"),

		EXECUTION_CONTEXTS("executionContexts"),

		ALL("*");

		private final String headerValue;

		Directive(String headerValue) {
			this.headerValue = "\"" + headerValue + "\"";
		}

		public String getHeaderValue() {
			return this.headerValue;
		}

	}

	private String transformToHeaderValue(Directive... directives) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < directives.length - 1; i++) {
			sb.append(directives[i].headerValue).append(", ");
		}
		sb.append(directives[directives.length - 1].headerValue);
		return sb.toString();
	}

	private boolean isSecure(ServerWebExchange exchange) {
		String scheme = exchange.getRequest().getURI().getScheme();
		return scheme != null && scheme.equalsIgnoreCase("https");
	}

}
