package org.springframework.security.web.http;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

/**
 * Utilities for interacting with {@link HttpHeaders}
 *
 * @author Rob Winch
 * @since 5.1
 */
public final class SecurityHeaders {

	private SecurityHeaders() {
	}

	/**
	 * Sets the provided value as a Bearer token in a header with the name of
	 * {@link HttpHeaders#AUTHORIZATION}
	 * @param bearerTokenValue the bear token value
	 * @return a {@link Consumer} that sets the header.
	 */
	public static Consumer<HttpHeaders> bearerToken(String bearerTokenValue) {
		Assert.hasText(bearerTokenValue, "bearerTokenValue cannot be null");
		return (headers) -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + bearerTokenValue);
	}

}
