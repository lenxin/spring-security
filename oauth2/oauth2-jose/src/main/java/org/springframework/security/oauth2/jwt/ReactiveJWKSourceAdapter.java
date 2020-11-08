package org.springframework.security.oauth2.jwt;

import java.util.List;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import reactor.core.publisher.Mono;

/**
 * Adapts a {@link JWKSource} to a {@link ReactiveJWKSource} which must be non-blocking.
 *
 * @author Rob Winch
 * @since 5.1
 */
class ReactiveJWKSourceAdapter implements ReactiveJWKSource {

	private final JWKSource<SecurityContext> source;

	/**
	 * Creates a new instance
	 * @param source
	 */
	ReactiveJWKSourceAdapter(JWKSource<SecurityContext> source) {
		this.source = source;
	}

	@Override
	public Mono<List<JWK>> get(JWKSelector jwkSelector) {
		return Mono.fromCallable(() -> this.source.get(jwkSelector, null));
	}

}
