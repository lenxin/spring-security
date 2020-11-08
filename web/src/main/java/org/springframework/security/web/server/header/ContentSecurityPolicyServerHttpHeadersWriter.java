package org.springframework.security.web.server.header;

import reactor.core.publisher.Mono;

import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter.Builder;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Writes the {@code Contet-Security-Policy} response header with configured policy
 * directives.
 *
 * @author Vedran Pavic
 * @since 5.1
 */
public final class ContentSecurityPolicyServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";

	public static final String CONTENT_SECURITY_POLICY_REPORT_ONLY = "Content-Security-Policy-Report-Only";

	private String policyDirectives;

	private boolean reportOnly;

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
		this.policyDirectives = policyDirectives;
		this.delegate = createDelegate();
	}

	/**
	 * Set whether to include the {@code Content-Security-Policy-Report-Only} header in
	 * the response. Otherwise, defaults to the {@code Content-Security-Policy} header.
	 * @param reportOnly whether to only report policy violations
	 */
	public void setReportOnly(boolean reportOnly) {
		this.reportOnly = reportOnly;
		this.delegate = createDelegate();
	}

	private ServerHttpHeadersWriter createDelegate() {
		if (this.policyDirectives == null) {
			return null;
		}
		Builder builder = StaticServerHttpHeadersWriter.builder();
		builder.header(resolveHeader(this.reportOnly), this.policyDirectives);
		return builder.build();
	}

	private static String resolveHeader(boolean reportOnly) {
		return reportOnly ? CONTENT_SECURITY_POLICY_REPORT_ONLY : CONTENT_SECURITY_POLICY;
	}

}
