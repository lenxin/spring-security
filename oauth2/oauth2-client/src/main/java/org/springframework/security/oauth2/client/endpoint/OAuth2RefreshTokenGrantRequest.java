package org.springframework.security.oauth2.client.endpoint;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.util.Assert;

/**
 * An OAuth 2.0 Refresh Token Grant request that holds the {@link OAuth2RefreshToken
 * refresh token} credential granted to the {@link #getClientRegistration() client}.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see AbstractOAuth2AuthorizationGrantRequest
 * @see OAuth2RefreshToken
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-6">Section 6
 * Refreshing an Access Token</a>
 */
public class OAuth2RefreshTokenGrantRequest extends AbstractOAuth2AuthorizationGrantRequest {

	private final ClientRegistration clientRegistration;

	private final OAuth2AccessToken accessToken;

	private final OAuth2RefreshToken refreshToken;

	private final Set<String> scopes;

	/**
	 * Constructs an {@code OAuth2RefreshTokenGrantRequest} using the provided parameters.
	 * @param clientRegistration the authorized client's registration
	 * @param accessToken the access token credential granted
	 * @param refreshToken the refresh token credential granted
	 */
	public OAuth2RefreshTokenGrantRequest(ClientRegistration clientRegistration, OAuth2AccessToken accessToken,
			OAuth2RefreshToken refreshToken) {
		this(clientRegistration, accessToken, refreshToken, Collections.emptySet());
	}

	/**
	 * Constructs an {@code OAuth2RefreshTokenGrantRequest} using the provided parameters.
	 * @param clientRegistration the authorized client's registration
	 * @param accessToken the access token credential granted
	 * @param refreshToken the refresh token credential granted
	 * @param scopes the scopes to request
	 */
	public OAuth2RefreshTokenGrantRequest(ClientRegistration clientRegistration, OAuth2AccessToken accessToken,
			OAuth2RefreshToken refreshToken, Set<String> scopes) {
		super(AuthorizationGrantType.REFRESH_TOKEN);
		Assert.notNull(clientRegistration, "clientRegistration cannot be null");
		Assert.notNull(accessToken, "accessToken cannot be null");
		Assert.notNull(refreshToken, "refreshToken cannot be null");
		this.clientRegistration = clientRegistration;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.scopes = Collections
				.unmodifiableSet((scopes != null) ? new LinkedHashSet<>(scopes) : Collections.emptySet());
	}

	/**
	 * Returns the authorized client's {@link ClientRegistration registration}.
	 * @return the {@link ClientRegistration}
	 */
	public ClientRegistration getClientRegistration() {
		return this.clientRegistration;
	}

	/**
	 * Returns the {@link OAuth2AccessToken access token} credential granted.
	 * @return the {@link OAuth2AccessToken}
	 */
	public OAuth2AccessToken getAccessToken() {
		return this.accessToken;
	}

	/**
	 * Returns the {@link OAuth2RefreshToken refresh token} credential granted.
	 * @return the {@link OAuth2RefreshToken}
	 */
	public OAuth2RefreshToken getRefreshToken() {
		return this.refreshToken;
	}

	/**
	 * Returns the scope(s) to request.
	 * @return the scope(s) to request
	 */
	public Set<String> getScopes() {
		return this.scopes;
	}

}
