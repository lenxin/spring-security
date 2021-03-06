package org.springframework.security.web.server.authentication;

import java.security.cert.X509Certificate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.server.reactive.SslInfo;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor;
import org.springframework.security.web.authentication.preauth.x509.X509TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ServerX509AuthenticationConverterTests {

	@Mock
	private X509PrincipalExtractor principalExtractor;

	@InjectMocks
	private ServerX509AuthenticationConverter converter;

	private X509Certificate certificate;

	private MockServerHttpRequest.BaseBuilder<?> request;

	@Before
	public void setUp() throws Exception {
		this.request = MockServerHttpRequest.get("/");
		this.certificate = X509TestUtils.buildTestCertificate();
		given(this.principalExtractor.extractPrincipal(any())).willReturn("Luke Taylor");
	}

	@Test
	public void shouldReturnNullForInvalidCertificate() {
		Authentication authentication = this.converter.convert(MockServerWebExchange.from(this.request.build()))
				.block();
		assertThat(authentication).isNull();
	}

	@Test
	public void shouldReturnAuthenticationForValidCertificate() {
		this.request.sslInfo(new MockSslInfo(this.certificate));
		Authentication authentication = this.converter.convert(MockServerWebExchange.from(this.request.build()))
				.block();
		assertThat(authentication.getName()).isEqualTo("Luke Taylor");
		assertThat(authentication.getCredentials()).isEqualTo(this.certificate);
	}

	class MockSslInfo implements SslInfo {

		private final X509Certificate[] peerCertificates;

		MockSslInfo(X509Certificate... peerCertificates) {
			this.peerCertificates = peerCertificates;
		}

		@Override
		public String getSessionId() {
			return "mock-session-id";
		}

		@Override
		public X509Certificate[] getPeerCertificates() {
			return this.peerCertificates;
		}

	}

}
