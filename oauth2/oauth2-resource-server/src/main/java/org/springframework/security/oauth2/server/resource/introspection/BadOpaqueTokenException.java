package org.springframework.security.oauth2.server.resource.introspection;

/**
 * An exception similar to
 * {@link org.springframework.security.authentication.BadCredentialsException} that
 * indicates an opaque token that is invalid in some way.
 *
 * @author Josh Cummings
 * @since 5.3
 */
public class BadOpaqueTokenException extends OAuth2IntrospectionException {

	public BadOpaqueTokenException(String message) {
		super(message);
	}

	public BadOpaqueTokenException(String message, Throwable cause) {
		super(message, cause);
	}

}
