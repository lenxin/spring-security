package org.springframework.security.oauth2.core.endpoint;

/**
 * @author Rob Winch
 * @since 5.1
 */
public final class TestOAuth2AuthorizationResponses {

	private TestOAuth2AuthorizationResponses() {
	}

	public static OAuth2AuthorizationResponse.Builder success() {
		// @formatter:off
		return OAuth2AuthorizationResponse.success("authorization-code")
				.state("state")
				.redirectUri("https://example.com/authorize/oauth2/code/registration-id");
		// @formatter:on
	}

	public static OAuth2AuthorizationResponse.Builder error() {
		// @formatter:off
		return OAuth2AuthorizationResponse.error("error")
				.redirectUri("https://example.com/authorize/oauth2/code/registration-id")
				.errorUri("https://example.com/error");
		// @formatter:on
	}

}
