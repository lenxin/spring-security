package org.springframework.security.oauth2.client.endpoint;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.TestOAuth2AuthorizationExchanges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OAuth2AuthorizationCodeGrantRequest}.
 *
 * @author Joe Grandja
 */
public class OAuth2AuthorizationCodeGrantRequestTests {

	private ClientRegistration clientRegistration;

	private OAuth2AuthorizationExchange authorizationExchange;

	@Before
	public void setUp() {
		this.clientRegistration = TestClientRegistrations.clientRegistration().build();
		this.authorizationExchange = TestOAuth2AuthorizationExchanges.success();
	}

	@Test
	public void constructorWhenClientRegistrationIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2AuthorizationCodeGrantRequest(null, this.authorizationExchange));
	}

	@Test
	public void constructorWhenAuthorizationExchangeIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2AuthorizationCodeGrantRequest(this.clientRegistration, null));
	}

	@Test
	public void constructorWhenAllParametersProvidedAndValidThenCreated() {
		OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest = new OAuth2AuthorizationCodeGrantRequest(
				this.clientRegistration, this.authorizationExchange);
		assertThat(authorizationCodeGrantRequest.getClientRegistration()).isEqualTo(this.clientRegistration);
		assertThat(authorizationCodeGrantRequest.getAuthorizationExchange()).isEqualTo(this.authorizationExchange);
		assertThat(authorizationCodeGrantRequest.getGrantType()).isEqualTo(AuthorizationGrantType.AUTHORIZATION_CODE);
	}

}
