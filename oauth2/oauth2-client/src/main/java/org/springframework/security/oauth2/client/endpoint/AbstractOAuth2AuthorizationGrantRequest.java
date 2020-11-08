package org.springframework.security.oauth2.client.endpoint;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

/**
 * Base implementation of an OAuth 2.0 Authorization Grant request that holds an
 * authorization grant credential and is used when initiating a request to the
 * Authorization Server's Token Endpoint.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see AuthorizationGrantType
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-1.3">Section
 * 1.3 Authorization Grant</a>
 */
public abstract class AbstractOAuth2AuthorizationGrantRequest {

	private final AuthorizationGrantType authorizationGrantType;

	/**
	 * Sub-class constructor.
	 * @param authorizationGrantType the authorization grant type
	 */
	protected AbstractOAuth2AuthorizationGrantRequest(AuthorizationGrantType authorizationGrantType) {
		Assert.notNull(authorizationGrantType, "authorizationGrantType cannot be null");
		this.authorizationGrantType = authorizationGrantType;
	}

	/**
	 * Returns the authorization grant type.
	 * @return the authorization grant type
	 */
	public AuthorizationGrantType getGrantType() {
		return this.authorizationGrantType;
	}

}
