package org.springframework.security.oauth2.client.authentication;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.TestOidcUsers;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.TestOAuth2Users;

/**
 * @author Josh Cummings
 * @since 5.2
 */
public final class TestOAuth2AuthenticationTokens {

	private TestOAuth2AuthenticationTokens() {
	}

	public static OAuth2AuthenticationToken authenticated() {
		DefaultOAuth2User principal = TestOAuth2Users.create();
		String registrationId = "registration-id";
		return new OAuth2AuthenticationToken(principal, principal.getAuthorities(), registrationId);
	}

	public static OAuth2AuthenticationToken oidcAuthenticated() {
		DefaultOidcUser principal = TestOidcUsers.create();
		String registrationId = "registration-id";
		return new OAuth2AuthenticationToken(principal, principal.getAuthorities(), registrationId);
	}

}
