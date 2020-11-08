package org.springframework.security.oauth2.client.endpoint;

import reactor.core.publisher.Mono;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

/**
 * A reactive strategy for &quot;exchanging&quot; an authorization grant credential (e.g.
 * an Authorization Code) for an access token credential at the Authorization Server's
 * Token Endpoint.
 *
 * @author Rob Winch
 * @since 5.1
 * @see AbstractOAuth2AuthorizationGrantRequest
 * @see OAuth2AccessTokenResponse
 * @see AuthorizationGrantType
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-1.3">Section
 * 1.3 Authorization Grant</a>
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-4.1.3">Section 4.1.3 Access Token Request
 * (Authorization Code Grant)</a>
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-4.1.4">Section 4.1.4 Access Token Response
 * (Authorization Code Grant)</a>
 */
@FunctionalInterface
public interface ReactiveOAuth2AccessTokenResponseClient<T extends AbstractOAuth2AuthorizationGrantRequest> {

	/**
	 * Exchanges the authorization grant credential, provided in the authorization grant
	 * request, for an access token credential at the Authorization Server's Token
	 * Endpoint.
	 * @param authorizationGrantRequest the authorization grant request that contains the
	 * authorization grant credential
	 * @return an {@link OAuth2AccessTokenResponse} that contains the
	 * {@link OAuth2AccessTokenResponse#getAccessToken() access token} credential
	 * @throws OAuth2AuthorizationException if an error occurs while attempting to
	 * exchange for the access token credential
	 */
	Mono<OAuth2AccessTokenResponse> getTokenResponse(T authorizationGrantRequest);

}
