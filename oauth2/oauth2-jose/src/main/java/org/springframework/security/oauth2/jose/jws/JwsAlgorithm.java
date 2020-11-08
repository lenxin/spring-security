package org.springframework.security.oauth2.jose.jws;

import org.springframework.security.oauth2.jose.JwaAlgorithm;

/**
 * Super interface for cryptographic algorithms defined by the JSON Web Algorithms (JWA)
 * specification and used by JSON Web Signature (JWS) to digitally sign or create a MAC of
 * the contents of the JWS Protected Header and JWS Payload.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see JwaAlgorithm
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc7518">JSON Web Algorithms
 * (JWA)</a>
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc7515">JSON Web Signature
 * (JWS)</a>
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc7518#section-3">Cryptographic Algorithms for Digital
 * Signatures and MACs</a>
 */
public interface JwsAlgorithm extends JwaAlgorithm {

}
