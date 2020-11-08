package org.springframework.security.oauth2.client.oidc.userinfo;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.util.Assert;

/**
 * Represents a request the {@link OidcUserService} uses when initiating a request to the
 * UserInfo Endpoint.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see ClientRegistration
 * @see OAuth2AccessToken
 * @see OidcIdToken
 * @see OidcUserService
 */
public class OidcUserRequest extends OAuth2UserRequest {

	private final OidcIdToken idToken;

	/**
	 * Constructs an {@code OidcUserRequest} using the provided parameters.
	 * @param clientRegistration the client registration
	 * @param accessToken the access token credential
	 * @param idToken the ID Token
	 */
	public OidcUserRequest(ClientRegistration clientRegistration, OAuth2AccessToken accessToken, OidcIdToken idToken) {
		this(clientRegistration, accessToken, idToken, Collections.emptyMap());
	}

	/**
	 * Constructs an {@code OidcUserRequest} using the provided parameters.
	 * @param clientRegistration the client registration
	 * @param accessToken the access token credential
	 * @param idToken the ID Token
	 * @param additionalParameters the additional parameters, may be empty
	 * @since 5.1
	 */
	public OidcUserRequest(ClientRegistration clientRegistration, OAuth2AccessToken accessToken, OidcIdToken idToken,
			Map<String, Object> additionalParameters) {
		super(clientRegistration, accessToken, additionalParameters);
		Assert.notNull(idToken, "idToken cannot be null");
		this.idToken = idToken;
	}

	/**
	 * Returns the {@link OidcIdToken ID Token} containing claims about the user.
	 * @return the {@link OidcIdToken} containing claims about the user.
	 */
	public OidcIdToken getIdToken() {
		return this.idToken;
	}

}
