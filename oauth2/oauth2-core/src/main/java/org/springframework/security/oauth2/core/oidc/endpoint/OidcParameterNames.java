package org.springframework.security.oauth2.core.oidc.endpoint;

/**
 * Standard parameter names defined in the OAuth Parameters Registry and used by the
 * authorization endpoint and token endpoint.
 *
 * @author Joe Grandja
 * @author Mark Heckler
 * @since 5.0
 * @see <a target="_blank" href=
 * "https://openid.net/specs/openid-connect-core-1_0.html#OAuthParametersRegistry">18.2
 * OAuth Parameters Registration</a>
 */
public interface OidcParameterNames {

	/**
	 * {@code id_token} - used in the Access Token Response.
	 */
	String ID_TOKEN = "id_token";

	/**
	 * {@code nonce} - used in the Authentication Request.
	 */
	String NONCE = "nonce";

}
