package org.springframework.security.web.server.authorization;

import java.nio.charset.Charset;

import reactor.core.publisher.Mono;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Sets the provided HTTP Status when access is denied.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class HttpStatusServerAccessDeniedHandler implements ServerAccessDeniedHandler {

	private final HttpStatus httpStatus;

	/**
	 * Creates an instance with the provided status
	 * @param httpStatus the status to use
	 */
	public HttpStatusServerAccessDeniedHandler(HttpStatus httpStatus) {
		Assert.notNull(httpStatus, "httpStatus cannot be null");
		this.httpStatus = httpStatus;
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
		return Mono.defer(() -> Mono.just(exchange.getResponse())).flatMap((response) -> {
			response.setStatusCode(this.httpStatus);
			response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
			DataBufferFactory dataBufferFactory = response.bufferFactory();
			DataBuffer buffer = dataBufferFactory.wrap(ex.getMessage().getBytes(Charset.defaultCharset()));
			return response.writeWith(Mono.just(buffer)).doOnError((error) -> DataBufferUtils.release(buffer));
		});
	}

}
