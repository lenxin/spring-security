package org.springframework.security.saml2.provider.service.registration;

import org.springframework.security.saml2.credentials.Saml2X509Credential;
import org.springframework.security.saml2.credentials.TestSaml2X509Credentials;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;

/**
 * Preconfigured test data for {@link RelyingPartyRegistration} objects
 */
public final class TestRelyingPartyRegistrations {

	private TestRelyingPartyRegistrations() {
	}

	public static RelyingPartyRegistration.Builder relyingPartyRegistration() {
		String registrationId = "simplesamlphp";
		String rpEntityId = "{baseUrl}/saml2/service-provider-metadata/{registrationId}";
		Saml2X509Credential signingCredential = TestSaml2X509Credentials.relyingPartySigningCredential();
		String assertionConsumerServiceLocation = "{baseUrl}"
				+ Saml2WebSsoAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;
		String apEntityId = "https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/metadata.php";
		Saml2X509Credential verificationCertificate = TestSaml2X509Credentials.relyingPartyVerifyingCredential();
		String singleSignOnServiceLocation = "https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SSOService.php";
		return RelyingPartyRegistration.withRegistrationId(registrationId).entityId(rpEntityId)
				.assertionConsumerServiceLocation(assertionConsumerServiceLocation)
				.credentials((c) -> c.add(signingCredential))
				.providerDetails((c) -> c.entityId(apEntityId).webSsoUrl(singleSignOnServiceLocation))
				.credentials((c) -> c.add(verificationCertificate));
	}

	public static RelyingPartyRegistration.Builder noCredentials() {
		return RelyingPartyRegistration.withRegistrationId("registration-id").entityId("rp-entity-id")
				.assertionConsumerServiceLocation("https://rp.example.org/acs").assertingPartyDetails((party) -> party
						.entityId("ap-entity-id").singleSignOnServiceLocation("https://ap.example.org/sso"));
	}

	public static RelyingPartyRegistration.Builder full() {
		return noCredentials()
				.signingX509Credentials((c) -> c.add(org.springframework.security.saml2.core.TestSaml2X509Credentials
						.relyingPartySigningCredential()))
				.decryptionX509Credentials((c) -> c.add(org.springframework.security.saml2.core.TestSaml2X509Credentials
						.relyingPartyDecryptingCredential()))
				.assertingPartyDetails((party) -> party.verificationX509Credentials(
						(c) -> c.add(org.springframework.security.saml2.core.TestSaml2X509Credentials
								.relyingPartyVerifyingCredential())));
	}

}
