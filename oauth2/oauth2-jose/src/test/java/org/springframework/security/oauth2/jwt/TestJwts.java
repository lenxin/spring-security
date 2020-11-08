package org.springframework.security.oauth2.jwt;

import java.time.Instant;
import java.util.Arrays;

public final class TestJwts {

	private TestJwts() {
	}

	public static Jwt.Builder jwt() {
		// @formatter:off
		return Jwt.withTokenValue("token")
				.header("alg", "none")
				.audience(Arrays.asList("https://audience.example.org"))
				.expiresAt(Instant.MAX)
				.issuedAt(Instant.MIN)
				.issuer("https://issuer.example.org")
				.jti("jti")
				.notBefore(Instant.MIN)
				.subject("mock-test-subject");
		// @formatter:on
	}

	public static Jwt user() {
		// @formatter:off
		return jwt()
				.claim("sub", "mock-test-subject")
				.build();
		// @formatter:on
	}

}
