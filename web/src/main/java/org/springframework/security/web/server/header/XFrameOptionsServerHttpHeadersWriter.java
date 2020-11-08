package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter.Builder;
import org.springframework.web.server.ServerWebExchange;

/**
 * {@code ServerHttpHeadersWriter} implementation for the X-Frame-Options headers.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class XFrameOptionsServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	public static final String X_FRAME_OPTIONS = "X-Frame-Options";

	private ServerHttpHeadersWriter delegate = createDelegate(Mode.DENY);

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		return this.delegate.writeHttpHeaders(exchange);
	}

	/**
	 * Sets the X-Frame-Options mode. There is no support for ALLOW-FROM because not
	 * <a href=
	 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">all
	 * browsers support it</a>. Consider using X-Frame-Options with
	 * Content-Security-Policy <a href=
	 * "https://w3c.github.io/webappsec/specs/content-security-policy/#directive-frame-ancestors">frame-ancestors</a>.
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.delegate = createDelegate(mode);
	}

	/**
	 * The X-Frame-Options values. There is no support for ALLOW-FROM because not <a href=
	 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">all
	 * browsers support it</a>. Consider using X-Frame-Options with
	 * Content-Security-Policy <a href=
	 * "https://w3c.github.io/webappsec/specs/content-security-policy/#directive-frame-ancestors">frame-ancestors</a>.
	 *
	 * @author Rob Winch
	 * @since 5.0
	 */
	public enum Mode {

		/**
		 * A browser receiving content with this header field MUST NOT display this
		 * content in any frame.
		 */
		DENY,

		/**
		 * A browser receiving content with this header field MUST NOT display this
		 * content in any frame from a page of different origin than the content itself.
		 *
		 * If a browser or plugin cannot reliably determine whether or not the origin of
		 * the content and the frame are the same, this MUST be treated as "DENY".
		 */
		SAMEORIGIN

	}

	private static ServerHttpHeadersWriter createDelegate(Mode mode) {
		Builder builder = StaticServerHttpHeadersWriter.builder();
		builder.header(X_FRAME_OPTIONS, mode.name());
		return builder.build();

	}

}
