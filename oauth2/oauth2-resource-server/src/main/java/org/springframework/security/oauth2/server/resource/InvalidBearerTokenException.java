package org.springframework.security.oauth2.server.resource;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

/**
 * An {@link OAuth2AuthenticationException} that indicates an invalid bearer token.
 *
 * @author Josh Cummings
 * @since 5.3
 */
public class InvalidBearerTokenException extends OAuth2AuthenticationException {

	/**
	 * Construct an instance of {@link InvalidBearerTokenException} given the provided
	 * description.
	 *
	 * The description will be wrapped into an
	 * {@link org.springframework.security.oauth2.core.OAuth2Error} instance as the
	 * {@code error_description}.
	 * @param description the description
	 */
	public InvalidBearerTokenException(String description) {
		super(BearerTokenErrors.invalidToken(description));
	}

	/**
	 * Construct an instance of {@link InvalidBearerTokenException} given the provided
	 * description and cause
	 *
	 * The description will be wrapped into an
	 * {@link org.springframework.security.oauth2.core.OAuth2Error} instance as the
	 * {@code error_description}.
	 * @param description the description
	 * @param cause the causing exception
	 */
	public InvalidBearerTokenException(String description, Throwable cause) {
		super(BearerTokenErrors.invalidToken(description), cause);
	}

}
