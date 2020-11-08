package org.springframework.security.web.server.header;

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
public class XXssProtectionServerHttpHeadersWriterTests {

	ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());

	HttpHeaders headers = this.exchange.getResponse().getHeaders();

	XXssProtectionServerHttpHeadersWriter writer = new XXssProtectionServerHttpHeadersWriter();

	@Test
	public void writeHeadersWhenNoHeadersThenWriteHeaders() {
		this.writer.writeHttpHeaders(this.exchange);
		assertThat(this.headers).hasSize(1);
		assertThat(this.headers.get(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION))
				.containsOnly("1 ; mode=block");
	}

	@Test
	public void writeHeadersWhenBlockFalseThenWriteHeaders() {
		this.writer.setBlock(false);
		this.writer.writeHttpHeaders(this.exchange);
		assertThat(this.headers).hasSize(1);
		assertThat(this.headers.get(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION)).containsOnly("1");
	}

	@Test
	public void writeHeadersWhenEnabledFalseThenWriteHeaders() {
		this.writer.setEnabled(false);
		this.writer.writeHttpHeaders(this.exchange);
		assertThat(this.headers).hasSize(1);
		assertThat(this.headers.get(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION)).containsOnly("0");
	}

	@Test
	public void writeHeadersWhenHeaderWrittenThenDoesNotOverrride() {
		String headerValue = "value";
		this.headers.set(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION, headerValue);
		this.writer.writeHttpHeaders(this.exchange);
		assertThat(this.headers).hasSize(1);
		assertThat(this.headers.get(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION)).containsOnly(headerValue);
	}

}
