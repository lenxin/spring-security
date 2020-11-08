package org.springframework.security.oauth2.client;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.TestOAuth2AccessTokens;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.entry;

/**
 * Tests for {@link OAuth2AuthorizationContext}.
 *
 * @author Joe Grandja
 */
public class OAuth2AuthorizationContextTests {

	private ClientRegistration clientRegistration;

	private OAuth2AuthorizedClient authorizedClient;

	private Authentication principal;

	@Before
	public void setup() {
		this.clientRegistration = TestClientRegistrations.clientRegistration().build();
		this.authorizedClient = new OAuth2AuthorizedClient(this.clientRegistration, "principal",
				TestOAuth2AccessTokens.scopes("read", "write"));
		this.principal = new TestingAuthenticationToken("principal", "password");
	}

	@Test
	public void withClientRegistrationWhenNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> OAuth2AuthorizationContext.withClientRegistration(null).build())
				.withMessage("clientRegistration cannot be null");
	}

	@Test
	public void withAuthorizedClientWhenNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> OAuth2AuthorizationContext.withAuthorizedClient(null).build())
				.withMessage("authorizedClient cannot be null");
	}

	@Test
	public void withClientRegistrationWhenPrincipalIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> OAuth2AuthorizationContext.withClientRegistration(this.clientRegistration).build())
				.withMessage("principal cannot be null");
	}

	@Test
	public void withAuthorizedClientWhenAllValuesProvidedThenAllValuesAreSet() {
		// @formatter:off
		OAuth2AuthorizationContext authorizationContext = OAuth2AuthorizationContext
				.withAuthorizedClient(this.authorizedClient)
				.principal(this.principal)
				.attributes((attributes) -> {
					attributes.put("attribute1", "value1");
					attributes.put("attribute2", "value2");
				})
				.build();
		// @formatter:on
		assertThat(authorizationContext.getClientRegistration()).isSameAs(this.clientRegistration);
		assertThat(authorizationContext.getAuthorizedClient()).isSameAs(this.authorizedClient);
		assertThat(authorizationContext.getPrincipal()).isSameAs(this.principal);
		assertThat(authorizationContext.getAttributes()).contains(entry("attribute1", "value1"),
				entry("attribute2", "value2"));
	}

}
