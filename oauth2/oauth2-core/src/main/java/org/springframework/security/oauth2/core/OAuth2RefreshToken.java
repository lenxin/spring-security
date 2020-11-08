package org.springframework.security.oauth2.core;

import java.time.Instant;

/**
 * An implementation of an {@link AbstractOAuth2Token} representing an OAuth 2.0 Refresh
 * Token.
 *
 * <p>
 * A refresh token is a credential that represents an authorization granted by the
 * resource owner to the client. It is used by the client to obtain a new access token
 * when the current access token becomes invalid or expires, or to obtain additional
 * access tokens with identical or narrower scope.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see OAuth2AccessToken
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-1.5">Section
 * 1.5 Refresh Token</a>
 */
public class OAuth2RefreshToken extends AbstractOAuth2Token {

	/**
	 * Constructs an {@code OAuth2RefreshToken} using the provided parameters.
	 * @param tokenValue the token value
	 * @param issuedAt the time at which the token was issued
	 */
	public OAuth2RefreshToken(String tokenValue, Instant issuedAt) {
		this(tokenValue, issuedAt, null);
	}

	/**
	 * Constructs an {@code OAuth2RefreshToken} using the provided parameters.
	 * @param tokenValue the token value
	 * @param issuedAt the time at which the token was issued
	 * @param expiresAt the time at which the token expires
	 * @since 5.5
	 */
	public OAuth2RefreshToken(String tokenValue, Instant issuedAt, Instant expiresAt) {
		super(tokenValue, issuedAt, expiresAt);
	}

}
