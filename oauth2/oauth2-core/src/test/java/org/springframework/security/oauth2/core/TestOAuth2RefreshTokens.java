package org.springframework.security.oauth2.core;

import java.time.Instant;

/**
 * @author Rob Winch
 * @since 5.1
 */
public final class TestOAuth2RefreshTokens {

	private TestOAuth2RefreshTokens() {
	}

	public static OAuth2RefreshToken refreshToken() {
		return new OAuth2RefreshToken("refresh-token", Instant.now());
	}

}
