package org.springframework.security.saml2.provider.service.authentication;

import org.springframework.security.saml2.provider.service.registration.TestRelyingPartyRegistrations;

/**
 * Test {@link Saml2AuthenticationRequestContext}s
 */
public final class TestSaml2AuthenticationRequestContexts {

	private TestSaml2AuthenticationRequestContexts() {
	}

	public static Saml2AuthenticationRequestContext.Builder authenticationRequestContext() {
		return Saml2AuthenticationRequestContext.builder().relayState("relayState").issuer("issuer")
				.relyingPartyRegistration(TestRelyingPartyRegistrations.relyingPartyRegistration().build())
				.assertionConsumerServiceUrl("assertionConsumerServiceUrl");
	}

}
