package org.springframework.security.oauth2.core;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Rob Winch
 * @since 5.1
 */
public final class TestOAuth2AccessTokens {

	private TestOAuth2AccessTokens() {
	}

	public static OAuth2AccessToken noScopes() {
		return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "no-scopes", Instant.now(),
				Instant.now().plus(Duration.ofDays(1)));
	}

	public static OAuth2AccessToken scopes(String... scopes) {
		return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "scopes", Instant.now(),
				Instant.now().plus(Duration.ofDays(1)), new HashSet<>(Arrays.asList(scopes)));
	}

}
