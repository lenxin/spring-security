package org.springframework.security.oauth2.core.endpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rob Winch
 * @since 5.1
 */
public final class TestOAuth2AuthorizationRequests {

	private TestOAuth2AuthorizationRequests() {
	}

	public static OAuth2AuthorizationRequest.Builder request() {
		String registrationId = "registration-id";
		String clientId = "client-id";
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(OAuth2ParameterNames.REGISTRATION_ID, registrationId);
		// @formatter:off
		return OAuth2AuthorizationRequest.authorizationCode()
				.authorizationUri("https://example.com/login/oauth/authorize")
				.clientId(clientId)
				.redirectUri("https://example.com/authorize/oauth2/code/registration-id")
				.state("state")
				.attributes(attributes);
		// @formatter:on
	}

	public static OAuth2AuthorizationRequest.Builder oidcRequest() {
		return request().scope("openid");
	}

}
