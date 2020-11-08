package org.springframework.security.oauth2.client.endpoint;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.util.Assert;

/**
 * An OAuth 2.0 Authorization Code Grant request that holds an Authorization Code
 * credential, which was granted by the Resource Owner to the
 * {@link #getClientRegistration() Client}.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see AbstractOAuth2AuthorizationGrantRequest
 * @see ClientRegistration
 * @see OAuth2AuthorizationExchange
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-1.3.1">Section 1.3.1 Authorization Code
 * Grant</a>
 */
public class OAuth2AuthorizationCodeGrantRequest extends AbstractOAuth2AuthorizationGrantRequest {

	private final ClientRegistration clientRegistration;

	private final OAuth2AuthorizationExchange authorizationExchange;

	/**
	 * Constructs an {@code OAuth2AuthorizationCodeGrantRequest} using the provided
	 * parameters.
	 * @param clientRegistration the client registration
	 * @param authorizationExchange the authorization exchange
	 */
	public OAuth2AuthorizationCodeGrantRequest(ClientRegistration clientRegistration,
			OAuth2AuthorizationExchange authorizationExchange) {
		super(AuthorizationGrantType.AUTHORIZATION_CODE);
		Assert.notNull(clientRegistration, "clientRegistration cannot be null");
		Assert.notNull(authorizationExchange, "authorizationExchange cannot be null");
		this.clientRegistration = clientRegistration;
		this.authorizationExchange = authorizationExchange;
	}

	/**
	 * Returns the {@link ClientRegistration client registration}.
	 * @return the {@link ClientRegistration}
	 */
	public ClientRegistration getClientRegistration() {
		return this.clientRegistration;
	}

	/**
	 * Returns the {@link OAuth2AuthorizationExchange authorization exchange}.
	 * @return the {@link OAuth2AuthorizationExchange}
	 */
	public OAuth2AuthorizationExchange getAuthorizationExchange() {
		return this.authorizationExchange;
	}

}
