package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter.Builder;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Writes the {@code Feature-Policy} response header with configured policy directives.
 *
 * @author Vedran Pavic
 * @since 5.1
 */
public final class FeaturePolicyServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	public static final String FEATURE_POLICY = "Feature-Policy";

	private ServerHttpHeadersWriter delegate;

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		return (this.delegate != null) ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
	}

	/**
	 * Set the policy directive(s) to be used in the response header.
	 * @param policyDirectives the policy directive(s)
	 * @throws IllegalArgumentException if policyDirectives is {@code null} or empty
	 */
	public void setPolicyDirectives(String policyDirectives) {
		Assert.hasLength(policyDirectives, "policyDirectives must not be null or empty");
		this.delegate = createDelegate(policyDirectives);
	}

	private static ServerHttpHeadersWriter createDelegate(String policyDirectives) {
		Builder builder = StaticServerHttpHeadersWriter.builder();
		builder.header(FEATURE_POLICY, policyDirectives);
		return builder.build();
	}

}
