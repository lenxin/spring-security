package org.springframework.security.oauth2.core.endpoint;

import org.springframework.util.Assert;

/**
 * An &quot;exchange&quot; of an OAuth 2.0 Authorization Request and Response for the
 * authorization code grant type.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see OAuth2AuthorizationRequest
 * @see OAuth2AuthorizationResponse
 */
public final class OAuth2AuthorizationExchange {

	private final OAuth2AuthorizationRequest authorizationRequest;

	private final OAuth2AuthorizationResponse authorizationResponse;

	/**
	 * Constructs a new {@code OAuth2AuthorizationExchange} with the provided
	 * Authorization Request and Authorization Response.
	 * @param authorizationRequest the {@link OAuth2AuthorizationRequest Authorization
	 * Request}
	 * @param authorizationResponse the {@link OAuth2AuthorizationResponse Authorization
	 * Response}
	 */
	public OAuth2AuthorizationExchange(OAuth2AuthorizationRequest authorizationRequest,
			OAuth2AuthorizationResponse authorizationResponse) {
		Assert.notNull(authorizationRequest, "authorizationRequest cannot be null");
		Assert.notNull(authorizationResponse, "authorizationResponse cannot be null");
		this.authorizationRequest = authorizationRequest;
		this.authorizationResponse = authorizationResponse;
	}

	/**
	 * Returns the {@link OAuth2AuthorizationRequest Authorization Request}.
	 * @return the {@link OAuth2AuthorizationRequest}
	 */
	public OAuth2AuthorizationRequest getAuthorizationRequest() {
		return this.authorizationRequest;
	}

	/**
	 * Returns the {@link OAuth2AuthorizationResponse Authorization Response}.
	 * @return the {@link OAuth2AuthorizationResponse}
	 */
	public OAuth2AuthorizationResponse getAuthorizationResponse() {
		return this.authorizationResponse;
	}

}
