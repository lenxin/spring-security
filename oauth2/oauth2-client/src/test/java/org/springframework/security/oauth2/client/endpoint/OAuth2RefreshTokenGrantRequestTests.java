package org.springframework.security.oauth2.client.endpoint;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.TestOAuth2AccessTokens;
import org.springframework.security.oauth2.core.TestOAuth2RefreshTokens;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OAuth2RefreshTokenGrantRequest}.
 *
 * @author Joe Grandja
 */
public class OAuth2RefreshTokenGrantRequestTests {

	private ClientRegistration clientRegistration;

	private OAuth2AccessToken accessToken;

	private OAuth2RefreshToken refreshToken;

	@Before
	public void setup() {
		this.clientRegistration = TestClientRegistrations.clientRegistration().build();
		this.accessToken = TestOAuth2AccessTokens.scopes("read", "write");
		this.refreshToken = TestOAuth2RefreshTokens.refreshToken();
	}

	@Test
	public void constructorWhenClientRegistrationIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2RefreshTokenGrantRequest(null, this.accessToken, this.refreshToken))
				.withMessage("clientRegistration cannot be null");
	}

	@Test
	public void constructorWhenAccessTokenIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2RefreshTokenGrantRequest(this.clientRegistration, null, this.refreshToken))
				.withMessage("accessToken cannot be null");
	}

	@Test
	public void constructorWhenRefreshTokenIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2RefreshTokenGrantRequest(this.clientRegistration, this.accessToken, null))
				.withMessage("refreshToken cannot be null");
	}

	@Test
	public void constructorWhenValidParametersProvidedThenCreated() {
		Set<String> scopes = new HashSet<>(Arrays.asList("read", "write"));
		OAuth2RefreshTokenGrantRequest refreshTokenGrantRequest = new OAuth2RefreshTokenGrantRequest(
				this.clientRegistration, this.accessToken, this.refreshToken, scopes);
		assertThat(refreshTokenGrantRequest.getClientRegistration()).isSameAs(this.clientRegistration);
		assertThat(refreshTokenGrantRequest.getAccessToken()).isSameAs(this.accessToken);
		assertThat(refreshTokenGrantRequest.getRefreshToken()).isSameAs(this.refreshToken);
		assertThat(refreshTokenGrantRequest.getScopes()).isEqualTo(scopes);
	}

}
