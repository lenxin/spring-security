package org.springframework.security.web.server.header;

import java.util.Arrays;
import java.util.Collections;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * Allows specifying {@link HttpHeaders} that should be written to the response.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class StaticServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	private final HttpHeaders headersToAdd;

	public StaticServerHttpHeadersWriter(HttpHeaders headersToAdd) {
		this.headersToAdd = headersToAdd;
	}

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		HttpHeaders headers = exchange.getResponse().getHeaders();
		boolean containsOneHeaderToAdd = Collections.disjoint(headers.keySet(), this.headersToAdd.keySet());
		if (containsOneHeaderToAdd) {
			this.headersToAdd.forEach(headers::put);
		}
		return Mono.empty();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private HttpHeaders headers = new HttpHeaders();

		public Builder header(String headerName, String... values) {
			this.headers.put(headerName, Arrays.asList(values));
			return this;
		}

		public StaticServerHttpHeadersWriter build() {
			return new StaticServerHttpHeadersWriter(this.headers);
		}

	}

}
