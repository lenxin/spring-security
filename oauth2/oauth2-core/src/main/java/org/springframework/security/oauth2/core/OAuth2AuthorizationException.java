package org.springframework.security.oauth2.core;

import org.springframework.util.Assert;

/**
 * Base exception for OAuth 2.0 Authorization errors.
 *
 * @author Joe Grandja
 * @since 5.1
 */
public class OAuth2AuthorizationException extends RuntimeException {

	private final OAuth2Error error;

	/**
	 * Constructs an {@code OAuth2AuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 */
	public OAuth2AuthorizationException(OAuth2Error error) {
		this(error, error.toString());
	}

	/**
	 * Constructs an {@code OAuth2AuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param message the exception message
	 * @since 5.3
	 */
	public OAuth2AuthorizationException(OAuth2Error error, String message) {
		super(message);
		Assert.notNull(error, "error must not be null");
		this.error = error;
	}

	/**
	 * Constructs an {@code OAuth2AuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param cause the root cause
	 */
	public OAuth2AuthorizationException(OAuth2Error error, Throwable cause) {
		this(error, error.toString(), cause);
	}

	/**
	 * Constructs an {@code OAuth2AuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param message the exception message
	 * @param cause the root cause
	 * @since 5.3
	 */
	public OAuth2AuthorizationException(OAuth2Error error, String message, Throwable cause) {
		super(message, cause);
		Assert.notNull(error, "error must not be null");
		this.error = error;
	}

	/**
	 * Returns the {@link OAuth2Error OAuth 2.0 Error}.
	 * @return the {@link OAuth2Error}
	 */
	public OAuth2Error getError() {
		return this.error;
	}

}
