package org.springframework.security.oauth2.client;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.TestOAuth2AccessTokens;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link AuthorizationCodeReactiveOAuth2AuthorizedClientProvider}.
 *
 * @author Joe Grandja
 */
public class AuthorizationCodeReactiveOAuth2AuthorizedClientProviderTests {

	private AuthorizationCodeReactiveOAuth2AuthorizedClientProvider authorizedClientProvider;

	private ClientRegistration clientRegistration;

	private OAuth2AuthorizedClient authorizedClient;

	private Authentication principal;

	@Before
	public void setup() {
		this.authorizedClientProvider = new AuthorizationCodeReactiveOAuth2AuthorizedClientProvider();
		this.clientRegistration = TestClientRegistrations.clientRegistration().build();
		this.authorizedClient = new OAuth2AuthorizedClient(this.clientRegistration, "principal",
				TestOAuth2AccessTokens.scopes("read", "write"));
		this.principal = new TestingAuthenticationToken("principal", "password");
	}

	@Test
	public void authorizeWhenContextIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.authorizedClientProvider.authorize(null).block());
	}

	@Test
	public void authorizeWhenNotAuthorizationCodeThenUnableToAuthorize() {
		ClientRegistration clientCredentialsClient = TestClientRegistrations.clientCredentials().build();
		// @formatter:off
		OAuth2AuthorizationContext authorizationContext = OAuth2AuthorizationContext
				.withClientRegistration(clientCredentialsClient)
				.principal(this.principal)
				.build();
		// @formatter:on
		assertThat(this.authorizedClientProvider.authorize(authorizationContext).block()).isNull();
	}

	@Test
	public void authorizeWhenAuthorizationCodeAndAuthorizedThenNotAuthorize() {
		// @formatter:off
		OAuth2AuthorizationContext authorizationContext = OAuth2AuthorizationContext
				.withAuthorizedClient(this.authorizedClient)
				.principal(this.principal)
				.build();
		// @formatter:on
		assertThat(this.authorizedClientProvider.authorize(authorizationContext).block()).isNull();
	}

	@Test
	public void authorizeWhenAuthorizationCodeAndNotAuthorizedThenAuthorize() {
		// @formatter:off
		OAuth2AuthorizationContext authorizationContext = OAuth2AuthorizationContext
				.withClientRegistration(this.clientRegistration)
				.principal(this.principal)
				.build();
		// @formatter:on
		assertThatExceptionOfType(ClientAuthorizationRequiredException.class)
				.isThrownBy(() -> this.authorizedClientProvider.authorize(authorizationContext).block());
	}

}
