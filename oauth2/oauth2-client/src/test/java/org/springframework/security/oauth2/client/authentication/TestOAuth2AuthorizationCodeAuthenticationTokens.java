package org.springframework.security.oauth2.client.authentication;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.TestOAuth2AccessTokens;
import org.springframework.security.oauth2.core.TestOAuth2RefreshTokens;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.TestOAuth2AuthorizationExchanges;

/**
 * @author Rob Winch
 * @since 5.1
 */
public final class TestOAuth2AuthorizationCodeAuthenticationTokens {

	private TestOAuth2AuthorizationCodeAuthenticationTokens() {
	}

	public static OAuth2AuthorizationCodeAuthenticationToken unauthenticated() {
		ClientRegistration registration = TestClientRegistrations.clientRegistration().build();
		OAuth2AuthorizationExchange exchange = TestOAuth2AuthorizationExchanges.success();
		return new OAuth2AuthorizationCodeAuthenticationToken(registration, exchange);
	}

	public static OAuth2AuthorizationCodeAuthenticationToken authenticated() {
		ClientRegistration registration = TestClientRegistrations.clientRegistration().build();
		OAuth2AuthorizationExchange exchange = TestOAuth2AuthorizationExchanges.success();
		OAuth2AccessToken accessToken = TestOAuth2AccessTokens.noScopes();
		OAuth2RefreshToken refreshToken = TestOAuth2RefreshTokens.refreshToken();
		return new OAuth2AuthorizationCodeAuthenticationToken(registration, exchange, accessToken, refreshToken);
	}

}
