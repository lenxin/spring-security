package org.springframework.security.oauth2.jwt;

/**
 * The Registered Claim Names defined by the JSON Web Token (JWT) specification that may
 * be contained in the JSON object JWT Claims Set.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see JwtClaimAccessor
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc7519#section-4">JWT
 * Claims</a>
 */
public interface JwtClaimNames {

	/**
	 * {@code iss} - the Issuer claim identifies the principal that issued the JWT
	 */
	String ISS = "iss";

	/**
	 * {@code sub} - the Subject claim identifies the principal that is the subject of the
	 * JWT
	 */
	String SUB = "sub";

	/**
	 * {@code aud} - the Audience claim identifies the recipient(s) that the JWT is
	 * intended for
	 */
	String AUD = "aud";

	/**
	 * {@code exp} - the Expiration time claim identifies the expiration time on or after
	 * which the JWT MUST NOT be accepted for processing
	 */
	String EXP = "exp";

	/**
	 * {@code nbf} - the Not Before claim identifies the time before which the JWT MUST
	 * NOT be accepted for processing
	 */
	String NBF = "nbf";

	/**
	 * {@code iat} - The Issued at claim identifies the time at which the JWT was issued
	 */
	String IAT = "iat";

	/**
	 * {@code jti} - The JWT ID claim provides a unique identifier for the JWT
	 */
	String JTI = "jti";

}
