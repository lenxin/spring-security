package org.springframework.security.web.server.header;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeaturePolicyServerHttpHeadersWriter}.
 *
 * @author Vedran Pavic
 */
public class FeaturePolicyServerHttpHeadersWriterTests {

	private static final String DEFAULT_POLICY_DIRECTIVES = "geolocation 'self'";

	private ServerWebExchange exchange;

	private FeaturePolicyServerHttpHeadersWriter writer;

	@Before
	public void setup() {
		this.exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/"));
		this.writer = new FeaturePolicyServerHttpHeadersWriter();
	}

	@Test
	public void writeHeadersWhenUsingDefaultsThenDoesNotWrite() {
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).isEmpty();
	}

	@Test
	public void writeHeadersWhenUsingPolicyThenWritesPolicy() {
		this.writer.setPolicyDirectives(DEFAULT_POLICY_DIRECTIVES);
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers.get(FeaturePolicyServerHttpHeadersWriter.FEATURE_POLICY))
				.containsOnly(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersWhenAlreadyWrittenThenWritesHeader() {
		this.writer.setPolicyDirectives(DEFAULT_POLICY_DIRECTIVES);
		String headerValue = "camera: 'self'";
		this.exchange.getResponse().getHeaders().set(FeaturePolicyServerHttpHeadersWriter.FEATURE_POLICY, headerValue);
		this.writer.writeHttpHeaders(this.exchange);
		HttpHeaders headers = this.exchange.getResponse().getHeaders();
		assertThat(headers).hasSize(1);
		assertThat(headers.get(FeaturePolicyServerHttpHeadersWriter.FEATURE_POLICY)).containsOnly(headerValue);
	}

}
