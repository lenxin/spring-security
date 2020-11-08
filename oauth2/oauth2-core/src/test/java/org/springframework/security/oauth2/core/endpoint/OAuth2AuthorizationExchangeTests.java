package org.springframework.security.oauth2.core.endpoint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OAuth2AuthorizationExchange}.
 *
 * @author Joe Grandja
 */
public class OAuth2AuthorizationExchangeTests {

	@Test
	public void constructorWhenAuthorizationRequestIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new OAuth2AuthorizationExchange(null, TestOAuth2AuthorizationResponses.success().build()));
	}

	@Test
	public void constructorWhenAuthorizationResponseIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new OAuth2AuthorizationExchange(TestOAuth2AuthorizationRequests.request().build(), null));
	}

	@Test
	public void constructorWhenRequiredArgsProvidedThenCreated() {
		OAuth2AuthorizationRequest authorizationRequest = TestOAuth2AuthorizationRequests.request().build();
		OAuth2AuthorizationResponse authorizationResponse = TestOAuth2AuthorizationResponses.success().build();
		OAuth2AuthorizationExchange authorizationExchange = new OAuth2AuthorizationExchange(authorizationRequest,
				authorizationResponse);
		assertThat(authorizationExchange.getAuthorizationRequest()).isEqualTo(authorizationRequest);
		assertThat(authorizationExchange.getAuthorizationResponse()).isEqualTo(authorizationResponse);
	}

}
