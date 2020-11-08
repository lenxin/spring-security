package org.springframework.security.oauth2.jwt;

import java.util.List;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import reactor.core.publisher.Mono;

/**
 * A reactive version of {@link com.nimbusds.jose.jwk.source.JWKSource}
 *
 * @author Rob Winch
 * @since 5.1
 */
interface ReactiveJWKSource {

	Mono<List<JWK>> get(JWKSelector jwkSelector);

}
