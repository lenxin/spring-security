package org.springframework.security.web.server.authentication.logout;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.util.Assert;

/**
 * <p>
 * A {@link ServerLogoutHandler} implementation which writes HTTP headers during logout.
 * </p>
 *
 * @author MD Sayem Ahmed
 * @since 5.2
 */
public final class HeaderWriterServerLogoutHandler implements ServerLogoutHandler {

	private final ServerHttpHeadersWriter headersWriter;

	/**
	 * <p>
	 * Constructs a new instance using the {@link ServerHttpHeadersWriter} implementation.
	 * </p>
	 * @param headersWriter a {@link ServerHttpHeadersWriter} implementation
	 * @throws IllegalArgumentException if the argument is null
	 */
	public HeaderWriterServerLogoutHandler(ServerHttpHeadersWriter headersWriter) {
		Assert.notNull(headersWriter, "headersWriter cannot be null");
		this.headersWriter = headersWriter;
	}

	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		return this.headersWriter.writeHttpHeaders(exchange.getExchange());
	}

}
