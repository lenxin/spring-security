package org.springframework.security.oauth2.core;

/**
 * Implementations of this interface are responsible for &quot;verifying&quot; the
 * validity and/or constraints of the attributes contained in an OAuth 2.0 Token.
 *
 * @author Joe Grandja
 * @author Josh Cummings
 * @since 5.1
 */
@FunctionalInterface
public interface OAuth2TokenValidator<T extends AbstractOAuth2Token> {

	/**
	 * Verify the validity and/or constraints of the provided OAuth 2.0 Token.
	 * @param token an OAuth 2.0 token
	 * @return OAuth2TokenValidationResult the success or failure detail of the validation
	 */
	OAuth2TokenValidatorResult validate(T token);

}
