package org.springframework.security.oauth2.jwt;

/**
 * Base exception for all JSON Web Token (JWT) related errors.
 *
 * @author Joe Grandja
 * @since 5.0
 */
public class JwtException extends RuntimeException {

	/**
	 * Constructs a {@code JwtException} using the provided parameters.
	 * @param message the detail message
	 */
	public JwtException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code JwtException} using the provided parameters.
	 * @param message the detail message
	 * @param cause the root cause
	 */
	public JwtException(String message, Throwable cause) {
		super(message, cause);
	}

}
