package org.springframework.security.saml2.provider.service.registration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.saml2.Saml2Exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link RelyingPartyRegistration}
 */
public class RelyingPartyRegistrationsTests {

	private String metadata;

	@Before
	public void setup() throws Exception {
		ClassPathResource resource = new ClassPathResource("test-metadata.xml");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
			this.metadata = reader.lines().collect(Collectors.joining());
		}
	}

	@Test
	public void fromMetadataUrlLocationWhenResolvableThenPopulatesBuilder() throws Exception {
		try (MockWebServer server = new MockWebServer()) {
			server.enqueue(new MockResponse().setBody(this.metadata).setResponseCode(200));
			RelyingPartyRegistration registration = RelyingPartyRegistrations
					.fromMetadataLocation(server.url("/").toString()).entityId("rp").build();
			RelyingPartyRegistration.AssertingPartyDetails details = registration.getAssertingPartyDetails();
			assertThat(details.getEntityId()).isEqualTo("https://idp.example.com/idp/shibboleth");
			assertThat(details.getSingleSignOnServiceLocation())
					.isEqualTo("https://idp.example.com/idp/profile/SAML2/POST/SSO");
			assertThat(details.getSingleSignOnServiceBinding()).isEqualTo(Saml2MessageBinding.POST);
			assertThat(details.getVerificationX509Credentials()).hasSize(1);
			assertThat(details.getEncryptionX509Credentials()).hasSize(1);
		}
	}

	@Test
	public void fromMetadataUrlLocationWhenUnresolvableThenSaml2Exception() throws Exception {
		try (MockWebServer server = new MockWebServer()) {
			server.enqueue(new MockResponse().setBody(this.metadata).setResponseCode(200));
			String url = server.url("/").toString();
			server.shutdown();
			assertThatExceptionOfType(Saml2Exception.class)
					.isThrownBy(() -> RelyingPartyRegistrations.fromMetadataLocation(url));
		}
	}

	@Test
	public void fromMetadataUrlLocationWhenMalformedResponseThenSaml2Exception() throws Exception {
		try (MockWebServer server = new MockWebServer()) {
			server.enqueue(new MockResponse().setBody("malformed").setResponseCode(200));
			String url = server.url("/").toString();
			assertThatExceptionOfType(Saml2Exception.class)
					.isThrownBy(() -> RelyingPartyRegistrations.fromMetadataLocation(url));
		}
	}

	@Test
	public void fromMetadataFileLocationWhenResolvableThenPopulatesBuilder() {
		File file = new File("src/test/resources/test-metadata.xml");
		RelyingPartyRegistration registration = RelyingPartyRegistrations
				.fromMetadataLocation("file:" + file.getAbsolutePath()).entityId("rp").build();
		RelyingPartyRegistration.AssertingPartyDetails details = registration.getAssertingPartyDetails();
		assertThat(details.getEntityId()).isEqualTo("https://idp.example.com/idp/shibboleth");
		assertThat(details.getSingleSignOnServiceLocation())
				.isEqualTo("https://idp.example.com/idp/profile/SAML2/POST/SSO");
		assertThat(details.getSingleSignOnServiceBinding()).isEqualTo(Saml2MessageBinding.POST);
		assertThat(details.getVerificationX509Credentials()).hasSize(1);
		assertThat(details.getEncryptionX509Credentials()).hasSize(1);
	}

	@Test
	public void fromMetadataFileLocationWhenNotFoundThenSaml2Exception() {
		assertThatExceptionOfType(Saml2Exception.class)
				.isThrownBy(() -> RelyingPartyRegistrations.fromMetadataLocation("filePath"));
	}

}
