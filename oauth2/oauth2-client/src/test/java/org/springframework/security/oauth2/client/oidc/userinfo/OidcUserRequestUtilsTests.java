package org.springframework.security.oauth2.client.oidc.userinfo;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

import org.junit.Test;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.TestOidcIdTokens;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.1
 */
public class OidcUserRequestUtilsTests {

	private ClientRegistration.Builder registration = TestClientRegistrations.clientRegistration();

	OidcIdToken idToken = TestOidcIdTokens.idToken().build();

	OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(),
			Instant.now().plus(Duration.ofDays(1)), Collections.singleton("read:user"));

	@Test
	public void shouldRetrieveUserInfoWhenEndpointDefinedAndScopesOverlapThenTrue() {
		assertThat(OidcUserRequestUtils.shouldRetrieveUserInfo(userRequest())).isTrue();
	}

	@Test
	public void shouldRetrieveUserInfoWhenNoUserInfoUriThenFalse() {
		this.registration.userInfoUri(null);
		assertThat(OidcUserRequestUtils.shouldRetrieveUserInfo(userRequest())).isFalse();
	}

	@Test
	public void shouldRetrieveUserInfoWhenDifferentScopesThenFalse() {
		this.registration.scope("notintoken");
		assertThat(OidcUserRequestUtils.shouldRetrieveUserInfo(userRequest())).isFalse();
	}

	@Test
	public void shouldRetrieveUserInfoWhenNotAuthorizationCodeThenFalse() {
		this.registration.authorizationGrantType(AuthorizationGrantType.IMPLICIT);
		assertThat(OidcUserRequestUtils.shouldRetrieveUserInfo(userRequest())).isFalse();
	}

	private OidcUserRequest userRequest() {
		return new OidcUserRequest(this.registration.build(), this.accessToken, this.idToken);
	}

}
