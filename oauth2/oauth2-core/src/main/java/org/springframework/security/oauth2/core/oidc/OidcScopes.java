package org.springframework.security.oauth2.core.oidc;

import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * The scope values defined by the OpenID Connect Core 1.0 specification that can be used
 * to request {@link StandardClaimNames claims}.
 * <p>
 * The scope(s) associated to an {@link OAuth2AccessToken} determine what claims
 * (resources) will be available when they are used to access OAuth 2.0 Protected
 * Endpoints, such as the UserInfo Endpoint.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see StandardClaimNames
 * @see <a target="_blank" href=
 * "https://openid.net/specs/openid-connect-core-1_0.html#ScopeClaims">Requesting Claims
 * using Scope Values</a>
 */
public interface OidcScopes {

	/**
	 * The {@code openid} scope is required for OpenID Connect Authentication Requests.
	 */
	String OPENID = "openid";

	/**
	 * The {@code profile} scope requests access to the default profile claims, which are:
	 * {@code name, family_name, given_name, middle_name, nickname, preferred_username,
	 * profile, picture, website, gender, birthdate, zoneinfo, locale, updated_at}.
	 */
	String PROFILE = "profile";

	/**
	 * The {@code email} scope requests access to the {@code email} and
	 * {@code email_verified} claims.
	 */
	String EMAIL = "email";

	/**
	 * The {@code address} scope requests access to the {@code address} claim.
	 */
	String ADDRESS = "address";

	/**
	 * The {@code phone} scope requests access to the {@code phone_number} and
	 * {@code phone_number_verified} claims.
	 */
	String PHONE = "phone";

}
