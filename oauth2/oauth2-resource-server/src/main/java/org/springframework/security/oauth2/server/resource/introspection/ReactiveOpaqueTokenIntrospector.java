package org.springframework.security.oauth2.server.resource.introspection;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * A contract for introspecting and verifying an OAuth 2.0 token.
 *
 * A typical implementation of this interface will make a request to an
 * <a href="https://tools.ietf.org/html/rfc7662" target="_blank">OAuth 2.0 Introspection
 * Endpoint</a> to verify the token and return its attributes, indicating a successful
 * verification.
 *
 * Another sensible implementation of this interface would be to query a backing store of
 * tokens, for example a distributed cache.
 *
 * @author Josh Cummings
 * @since 5.2
 */
@FunctionalInterface
public interface ReactiveOpaqueTokenIntrospector {

	/**
	 * Introspect and verify the given token, returning its attributes.
	 *
	 * Returning a {@link Map} is indicative that the token is valid.
	 * @param token the token to introspect
	 * @return the token's attributes
	 */
	Mono<OAuth2AuthenticatedPrincipal> introspect(String token);

}
