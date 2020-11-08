package org.springframework.security.oauth2.core;

import java.time.Instant;

import org.springframework.lang.Nullable;

/**
 * Core interface representing an OAuth 2.0 Token.
 *
 * @author Joe Grandja
 * @since 5.5
 * @see AbstractOAuth2Token
 */
public interface OAuth2Token {

	/**
	 * Returns the token value.
	 * @return the token value
	 */
	String getTokenValue();

	/**
	 * Returns the time at which the token was issued.
	 * @return the time the token was issued or {@code null}
	 */
	@Nullable
	default Instant getIssuedAt() {
		return null;
	}

	/**
	 * Returns the expiration time on or after which the token MUST NOT be accepted.
	 * @return the token expiration time or {@code null}
	 */
	@Nullable
	default Instant getExpiresAt() {
		return null;
	}

}
