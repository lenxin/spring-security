package org.springframework.security.oauth2.client.oidc.userinfo;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.TestOidcIdTokens;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OidcUserRequest}.
 *
 * @author Joe Grandja
 */
public class OidcUserRequestTests {

	private ClientRegistration clientRegistration;

	private OAuth2AccessToken accessToken;

	private OidcIdToken idToken;

	private Map<String, Object> additionalParameters;

	@Before
	public void setUp() {
		this.clientRegistration = TestClientRegistrations.clientRegistration().build();
		this.accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "access-token-1234", Instant.now(),
				Instant.now().plusSeconds(60), new LinkedHashSet<>(Arrays.asList("scope1", "scope2")));
		this.idToken = TestOidcIdTokens.idToken().authorizedParty(this.clientRegistration.getClientId()).build();
		this.additionalParameters = new HashMap<>();
		this.additionalParameters.put("param1", "value1");
		this.additionalParameters.put("param2", "value2");
	}

	@Test
	public void constructorWhenClientRegistrationIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OidcUserRequest(null, this.accessToken, this.idToken));
	}

	@Test
	public void constructorWhenAccessTokenIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OidcUserRequest(this.clientRegistration, null, this.idToken));
	}

	@Test
	public void constructorWhenIdTokenIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OidcUserRequest(this.clientRegistration, this.accessToken, null));
	}

	@Test
	public void constructorWhenAllParametersProvidedAndValidThenCreated() {
		OidcUserRequest userRequest = new OidcUserRequest(this.clientRegistration, this.accessToken, this.idToken,
				this.additionalParameters);
		assertThat(userRequest.getClientRegistration()).isEqualTo(this.clientRegistration);
		assertThat(userRequest.getAccessToken()).isEqualTo(this.accessToken);
		assertThat(userRequest.getIdToken()).isEqualTo(this.idToken);
		assertThat(userRequest.getAdditionalParameters()).containsAllEntriesOf(this.additionalParameters);
	}

}
