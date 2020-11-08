package org.springframework.security.oauth2.core.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;

/**
 * @author Rob Winch
 * @since 5.1
 */
public final class TestOAuth2AccessTokenResponses {

	private TestOAuth2AccessTokenResponses() {
	}

	public static OAuth2AccessTokenResponse.Builder accessTokenResponse() {
		// @formatter:off
		return OAuth2AccessTokenResponse
				.withToken("token")
				.tokenType(OAuth2AccessToken.TokenType.BEARER);
		// @formatter:on
	}

	public static OAuth2AccessTokenResponse.Builder oidcAccessTokenResponse() {
		Map<String, Object> additionalParameters = new HashMap<>();
		additionalParameters.put(OidcParameterNames.ID_TOKEN, "id-token");
		return accessTokenResponse().additionalParameters(additionalParameters);
	}

}
