package org.springframework.security.web.server.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter.Builder;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Writes the {@code Referrer-Policy} response header.
 *
 * @author Vedran Pavic
 * @since 5.1
 */
public final class ReferrerPolicyServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	public static final String REFERRER_POLICY = "Referrer-Policy";

	private ServerHttpHeadersWriter delegate;

	public ReferrerPolicyServerHttpHeadersWriter() {
		this.delegate = createDelegate(ReferrerPolicy.NO_REFERRER);
	}

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		return this.delegate.writeHttpHeaders(exchange);
	}

	/**
	 * Set the policy to be used in the response header.
	 * @param policy the policy
	 * @throws IllegalArgumentException if policy is {@code null}
	 */
	public void setPolicy(ReferrerPolicy policy) {
		Assert.notNull(policy, "policy must not be null");
		this.delegate = createDelegate(policy);
	}

	private static ServerHttpHeadersWriter createDelegate(ReferrerPolicy policy) {
		Builder builder = StaticServerHttpHeadersWriter.builder();
		builder.header(REFERRER_POLICY, policy.getPolicy());
		return builder.build();
	}

	public enum ReferrerPolicy {

		NO_REFERRER("no-referrer"),

		NO_REFERRER_WHEN_DOWNGRADE("no-referrer-when-downgrade"),

		SAME_ORIGIN("same-origin"),

		ORIGIN("origin"),

		STRICT_ORIGIN("strict-origin"),

		ORIGIN_WHEN_CROSS_ORIGIN("origin-when-cross-origin"),

		STRICT_ORIGIN_WHEN_CROSS_ORIGIN("strict-origin-when-cross-origin"),

		UNSAFE_URL("unsafe-url");

		private static final Map<String, ReferrerPolicy> REFERRER_POLICIES;

		static {
			Map<String, ReferrerPolicy> referrerPolicies = new HashMap<>();
			for (ReferrerPolicy referrerPolicy : values()) {
				referrerPolicies.put(referrerPolicy.getPolicy(), referrerPolicy);
			}
			REFERRER_POLICIES = Collections.unmodifiableMap(referrerPolicies);
		}

		private final String policy;

		ReferrerPolicy(String policy) {
			this.policy = policy;
		}

		public String getPolicy() {
			return this.policy;
		}

	}

}
