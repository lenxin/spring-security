package org.springframework.security.oauth2.client;

import java.util.Collections;

import org.junit.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.TestOAuth2AccessTokens;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DelegatingOAuth2AuthorizedClientProvider}.
 *
 * @author Joe Grandja
 */
public class DelegatingOAuth2AuthorizedClientProviderTests {

	@Test
	public void constructorWhenProvidersIsEmptyThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingOAuth2AuthorizedClientProvider(new OAuth2AuthorizedClientProvider[0]));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingOAuth2AuthorizedClientProvider(Collections.emptyList()));
	}

	@Test
	public void authorizeWhenContextIsNullThenThrowIllegalArgumentException() {
		DelegatingOAuth2AuthorizedClientProvider delegate = new DelegatingOAuth2AuthorizedClientProvider(
				mock(OAuth2AuthorizedClientProvider.class));
		assertThatIllegalArgumentException().isThrownBy(() -> delegate.authorize(null))
				.withMessage("context cannot be null");
	}

	@Test
	public void authorizeWhenProviderCanAuthorizeThenReturnAuthorizedClient() {
		Authentication principal = new TestingAuthenticationToken("principal", "password");
		ClientRegistration clientRegistration = TestClientRegistrations.clientRegistration().build();
		OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(clientRegistration, principal.getName(),
				TestOAuth2AccessTokens.noScopes());
		OAuth2AuthorizedClientProvider authorizedClientProvider = mock(OAuth2AuthorizedClientProvider.class);
		given(authorizedClientProvider.authorize(any())).willReturn(authorizedClient);
		DelegatingOAuth2AuthorizedClientProvider delegate = new DelegatingOAuth2AuthorizedClientProvider(
				mock(OAuth2AuthorizedClientProvider.class), mock(OAuth2AuthorizedClientProvider.class),
				authorizedClientProvider);
		OAuth2AuthorizationContext context = OAuth2AuthorizationContext.withClientRegistration(clientRegistration)
				.principal(principal).build();
		OAuth2AuthorizedClient reauthorizedClient = delegate.authorize(context);
		assertThat(reauthorizedClient).isSameAs(authorizedClient);
	}

	@Test
	public void authorizeWhenProviderCantAuthorizeThenReturnNull() {
		ClientRegistration clientRegistration = TestClientRegistrations.clientRegistration().build();
		OAuth2AuthorizationContext context = OAuth2AuthorizationContext.withClientRegistration(clientRegistration)
				.principal(new TestingAuthenticationToken("principal", "password")).build();
		DelegatingOAuth2AuthorizedClientProvider delegate = new DelegatingOAuth2AuthorizedClientProvider(
				mock(OAuth2AuthorizedClientProvider.class), mock(OAuth2AuthorizedClientProvider.class));
		assertThat(delegate.authorize(context)).isNull();
	}

}
