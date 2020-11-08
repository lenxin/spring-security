package org.springframework.security.oauth2.client.endpoint;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

/**
 * An OAuth 2.0 Client Credentials Grant request that holds the client's credentials in
 * {@link #getClientRegistration()}.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see AbstractOAuth2AuthorizationGrantRequest
 * @see ClientRegistration
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-1.3.4">Section 1.3.4 Client Credentials
 * Grant</a>
 */
public class OAuth2ClientCredentialsGrantRequest extends AbstractOAuth2AuthorizationGrantRequest {

	private final ClientRegistration clientRegistration;

	/**
	 * Constructs an {@code OAuth2ClientCredentialsGrantRequest} using the provided
	 * parameters.
	 * @param clientRegistration the client registration
	 */
	public OAuth2ClientCredentialsGrantRequest(ClientRegistration clientRegistration) {
		super(AuthorizationGrantType.CLIENT_CREDENTIALS);
		Assert.notNull(clientRegistration, "clientRegistration cannot be null");
		Assert.isTrue(AuthorizationGrantType.CLIENT_CREDENTIALS.equals(clientRegistration.getAuthorizationGrantType()),
				"clientRegistration.authorizationGrantType must be AuthorizationGrantType.CLIENT_CREDENTIALS");
		this.clientRegistration = clientRegistration;
	}

	/**
	 * Returns the {@link ClientRegistration client registration}.
	 * @return the {@link ClientRegistration}
	 */
	public ClientRegistration getClientRegistration() {
		return this.clientRegistration;
	}

}
