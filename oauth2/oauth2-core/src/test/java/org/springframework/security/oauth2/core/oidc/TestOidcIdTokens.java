package org.springframework.security.oauth2.core.oidc;

import java.time.Instant;

/**
 * Test {@link OidcIdToken}s
 *
 * @author Josh Cummings
 */
public final class TestOidcIdTokens {

	private TestOidcIdTokens() {
	}

	public static OidcIdToken.Builder idToken() {
		// @formatter:off
		return OidcIdToken.withTokenValue("id-token")
				.issuer("https://example.com")
				.subject("subject")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now()
				.plusSeconds(86400))
				.claim("id", "id");
		// @formatter:on
	}

}
