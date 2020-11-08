package org.springframework.security.oauth2.jose;

/**
 * Super interface for cryptographic algorithms defined by the JSON Web Algorithms (JWA)
 * specification and used by JSON Web Signature (JWS) to digitally sign or create a MAC of
 * the contents and JSON Web Encryption (JWE) to encrypt the contents.
 *
 * @author Joe Grandja
 * @since 5.5
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc7518">JSON Web Algorithms
 * (JWA)</a>
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc7515">JSON Web Signature
 * (JWS)</a>
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc7516">JSON Web Encryption
 * (JWE)</a>
 */
public interface JwaAlgorithm {

	/**
	 * Returns the algorithm name.
	 * @return the algorithm name
	 */
	String getName();

}
