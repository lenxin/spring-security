package org.springframework.security.oauth2.server.resource.introspection;

/**
 * Base exception for all OAuth 2.0 Introspection related errors
 *
 * @author Josh Cummings
 * @since 5.2
 */
public class OAuth2IntrospectionException extends RuntimeException {

	public OAuth2IntrospectionException(String message) {
		super(message);
	}

	public OAuth2IntrospectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
