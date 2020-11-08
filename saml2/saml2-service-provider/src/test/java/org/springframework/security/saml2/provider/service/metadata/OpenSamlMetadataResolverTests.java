package org.springframework.security.saml2.provider.service.metadata;

import org.junit.Test;

import org.springframework.security.saml2.core.TestSaml2X509Credentials;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.Saml2MessageBinding;
import org.springframework.security.saml2.provider.service.registration.TestRelyingPartyRegistrations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OpenSamlMetadataResolver}
 */
public class OpenSamlMetadataResolverTests {

	@Test
	public void resolveWhenRelyingPartyThenMetadataMatches() {
		RelyingPartyRegistration relyingPartyRegistration = TestRelyingPartyRegistrations.full()
				.assertionConsumerServiceBinding(Saml2MessageBinding.REDIRECT).build();
		OpenSamlMetadataResolver openSamlMetadataResolver = new OpenSamlMetadataResolver();
		String metadata = openSamlMetadataResolver.resolve(relyingPartyRegistration);
		assertThat(metadata).contains("<EntityDescriptor").contains("entityID=\"rp-entity-id\"")
				.contains("WantAssertionsSigned=\"true\"").contains("<md:KeyDescriptor use=\"signing\">")
				.contains("<md:KeyDescriptor use=\"encryption\">")
				.contains("<ds:X509Certificate>MIICgTCCAeoCCQCuVzyqFgMSyDANBgkqhkiG9w0BAQsFADCBhDELMAkGA1UEBh")
				.contains("Binding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect\"")
				.contains("Location=\"https://rp.example.org/acs\" index=\"1\"");
	}

	@Test
	public void resolveWhenRelyingPartyNoCredentialsThenMetadataMatches() {
		RelyingPartyRegistration relyingPartyRegistration = TestRelyingPartyRegistrations.noCredentials()
				.assertingPartyDetails((party) -> party.verificationX509Credentials(
						(c) -> c.add(TestSaml2X509Credentials.relyingPartyVerifyingCredential())))
				.build();
		OpenSamlMetadataResolver openSamlMetadataResolver = new OpenSamlMetadataResolver();
		String metadata = openSamlMetadataResolver.resolve(relyingPartyRegistration);
		assertThat(metadata).contains("<EntityDescriptor").contains("entityID=\"rp-entity-id\"")
				.contains("WantAssertionsSigned=\"true\"").doesNotContain("<md:KeyDescriptor use=\"signing\">")
				.doesNotContain("<md:KeyDescriptor use=\"encryption\">")
				.contains("Binding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\"")
				.contains("Location=\"https://rp.example.org/acs\" index=\"1\"");
	}

}
