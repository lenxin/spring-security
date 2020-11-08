package org.springframework.security.oauth2.jwt;

/**
 * An exception similar to
 * {@link org.springframework.security.authentication.BadCredentialsException} that
 * indicates a {@link Jwt} that is invalid in some way.
 *
 * @author Josh Cummings
 * @since 5.3
 */
public class BadJwtException extends JwtException {

	public BadJwtException(String message) {
		super(message);
	}

	public BadJwtException(String message, Throwable cause) {
		super(message, cause);
	}

}
