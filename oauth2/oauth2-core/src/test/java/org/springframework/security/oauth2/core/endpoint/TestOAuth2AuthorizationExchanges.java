package org.springframework.security.oauth2.core.endpoint;

/**
 * @author Rob Winch
 * @author Eddú Meléndez
 * @since 5.1
 */
public final class TestOAuth2AuthorizationExchanges {

	private TestOAuth2AuthorizationExchanges() {
	}

	public static OAuth2AuthorizationExchange success() {
		OAuth2AuthorizationRequest request = TestOAuth2AuthorizationRequests.request().build();
		OAuth2AuthorizationResponse response = TestOAuth2AuthorizationResponses.success().build();
		return new OAuth2AuthorizationExchange(request, response);
	}

	public static OAuth2AuthorizationExchange failure() {
		OAuth2AuthorizationRequest request = TestOAuth2AuthorizationRequests.request().build();
		OAuth2AuthorizationResponse response = TestOAuth2AuthorizationResponses.error().build();
		return new OAuth2AuthorizationExchange(request, response);
	}

}
