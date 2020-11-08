package org.springframework.security.saml2.provider.service.authentication;

import java.util.UUID;

import org.junit.Test;

import org.springframework.security.saml2.credentials.TestSaml2X509Credentials;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Saml2AuthenticationRequestFactory} default interface methods
 */
public class Saml2AuthenticationRequestFactoryTests {

	private RelyingPartyRegistration registration = RelyingPartyRegistration.withRegistrationId("id")
			.assertionConsumerServiceUrlTemplate("template")
			.providerDetails((c) -> c.webSsoUrl("https://example.com/destination"))
			.providerDetails((c) -> c.entityId("remote-entity-id")).localEntityIdTemplate("local-entity-id")
			.credentials((c) -> c.add(TestSaml2X509Credentials.relyingPartySigningCredential())).build();

	@Test
	public void createAuthenticationRequestParametersWhenRedirectDefaultIsUsedMessageIsDeflatedAndEncoded() {
		final String value = "Test String: " + UUID.randomUUID().toString();
		Saml2AuthenticationRequestFactory factory = (request) -> value;
		Saml2AuthenticationRequestContext request = Saml2AuthenticationRequestContext.builder()
				.relyingPartyRegistration(this.registration).issuer("https://example.com/issuer")
				.assertionConsumerServiceUrl("https://example.com/acs-url").build();
		Saml2RedirectAuthenticationRequest response = factory.createRedirectAuthenticationRequest(request);
		String resultValue = response.getSamlRequest();
		byte[] decoded = Saml2Utils.samlDecode(resultValue);
		String inflated = Saml2Utils.samlInflate(decoded);
		assertThat(inflated).isEqualTo(value);
	}

	@Test
	public void createAuthenticationRequestParametersWhenPostDefaultIsUsedMessageIsEncoded() {
		final String value = "Test String: " + UUID.randomUUID().toString();
		Saml2AuthenticationRequestFactory factory = (request) -> value;
		Saml2AuthenticationRequestContext request = Saml2AuthenticationRequestContext.builder()
				.relyingPartyRegistration(this.registration).issuer("https://example.com/issuer")
				.assertionConsumerServiceUrl("https://example.com/acs-url").build();
		Saml2PostAuthenticationRequest response = factory.createPostAuthenticationRequest(request);
		String resultValue = response.getSamlRequest();
		byte[] decoded = Saml2Utils.samlDecode(resultValue);
		assertThat(new String(decoded)).isEqualTo(value);
	}

}
