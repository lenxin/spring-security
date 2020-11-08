package org.springframework.security.test.web.servlet.request;

import java.security.cert.X509Certificate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.x509;

@RunWith(MockitoJUnitRunner.class)
public class SecurityMockMvcRequestPostProcessorsCertificateTests {

	@Mock
	private X509Certificate certificate;

	private MockHttpServletRequest request;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
	}

	@Test
	public void x509SingleCertificate() {
		MockHttpServletRequest postProcessedRequest = x509(this.certificate).postProcessRequest(this.request);
		X509Certificate[] certificates = (X509Certificate[]) postProcessedRequest
				.getAttribute("javax.servlet.request.X509Certificate");
		assertThat(certificates).containsOnly(this.certificate);
	}

	@Test
	public void x509ResourceName() throws Exception {
		MockHttpServletRequest postProcessedRequest = x509("rod.cer").postProcessRequest(this.request);
		X509Certificate[] certificates = (X509Certificate[]) postProcessedRequest
				.getAttribute("javax.servlet.request.X509Certificate");
		assertThat(certificates).hasSize(1);
		assertThat(certificates[0].getSubjectDN().getName())
				.isEqualTo("CN=rod, OU=Spring Security, O=Spring Framework");
	}

}
