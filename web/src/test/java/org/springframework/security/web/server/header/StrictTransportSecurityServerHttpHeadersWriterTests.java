package org.springframework.security.web.server.header;

import java.time.Duration;
import java.util.Arrays;

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
public class StrictTransportSecurityServerHttpHeadersWriterTests {

	StrictTransportSecurityServerHttpHeadersWriter hsts = new StrictTransportSecurityServerHttpHeadersWriter();

	ServerWebExchange exchange;

	@Test
	public void writeHttpHeadersWhenHttpsThenWrites() {
		this.exchange = exchange(MockServerHttpRequest.get("https://example.com/"));
		this.hsts.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers).containsEntry(StrictTransportSecurityServerHttpHeadersWriter.STRICT_TRANSPORT_SECURITY,
				Arrays.asList("max-age=31536000 ; includeSubDomains"));
	}

	@Test
	public void writeHttpHeadersWhenCustomMaxAgeThenWrites() {
		Duration maxAge = Duration.ofDays(1);
		this.hsts.setMaxAge(maxAge);
		this.exchange = exchange(MockServerHttpRequest.get("https://example.com/"));
		this.hsts.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers).containsEntry(StrictTransportSecurityServerHttpHeadersWriter.STRICT_TRANSPORT_SECURITY,
				Arrays.asList("max-age=" + maxAge.getSeconds() + " ; includeSubDomains"));
	}

	@Test
	public void writeHttpHeadersWhenCustomIncludeSubDomainsThenWrites() {
		this.hsts.setIncludeSubDomains(false);
		this.exchange = exchange(MockServerHttpRequest.get("https://example.com/"));
		this.hsts.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers).containsEntry(StrictTransportSecurityServerHttpHeadersWriter.STRICT_TRANSPORT_SECURITY,
				Arrays.asList("max-age=31536000"));
	}

	@Test
	public void writeHttpHeadersWhenNullSchemeThenNoHeaders() {
		this.exchange = exchange(MockServerHttpRequest.get("/"));
		this.hsts.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).isEmpty();
	}

	@Test
	public void writeHttpHeadersWhenHttpThenNoHeaders() {
		this.exchange = exchange(MockServerHttpRequest.get("http://localhost/"));
		this.hsts.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).isEmpty();
	}

	private static MockServerWebExchange exchange(MockServerHttpRequest.BaseBuilder<?> request) {
		return MockServerWebExchange.from(request.build());
	}

}
