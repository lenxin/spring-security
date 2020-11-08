package org.springframework.security.web.server.header;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class XFrameOptionsServerHttpHeadersWriterTests {

	ServerWebExchange exchange = exchange(MockServerHttpRequest.get("/"));

	XFrameOptionsServerHttpHeadersWriter writer;

	@Before
	public void setup() {
		this.writer = new XFrameOptionsServerHttpHeadersWriter();
	}

	@Test
	public void writeHeadersWhenUsingDefaultsThenWritesDeny() {
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers.get(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS)).containsOnly("DENY");
	}

	@Test
	public void writeHeadersWhenUsingExplicitDenyThenWritesDeny() {
		this.writer.setMode(XFrameOptionsServerHttpHeadersWriter.Mode.DENY);
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers.get(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS)).containsOnly("DENY");
	}

	@Test
	public void writeHeadersWhenUsingSameOriginThenWritesSameOrigin() {
		this.writer.setMode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN);
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers.get(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS)).containsOnly("SAMEORIGIN");
	}

	@Test
	public void writeHeadersWhenAlreadyWrittenThenWritesHeader() {
		String headerValue = "other";
		this.exchange.getResponse().getHeaders().set(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, headerValue);
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers.get(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS)).containsOnly(headerValue);
	}

	private static MockServerWebExchange exchange(MockServerHttpRequest.BaseBuilder<?> request) {
		return MockServerWebExchange.from(request.build());
	}

}
