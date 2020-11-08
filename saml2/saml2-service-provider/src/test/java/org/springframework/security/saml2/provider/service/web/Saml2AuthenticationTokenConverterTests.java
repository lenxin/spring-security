package org.springframework.security.saml2.provider.service.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.saml2.core.Saml2Utils;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticationToken;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.TestRelyingPartyRegistrations;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class Saml2AuthenticationTokenConverterTests {

	@Mock
	Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver;

	RelyingPartyRegistration relyingPartyRegistration = TestRelyingPartyRegistrations.relyingPartyRegistration()
			.build();

	@Test
	public void convertWhenSamlResponseThenToken() {
		Saml2AuthenticationTokenConverter converter = new Saml2AuthenticationTokenConverter(
				this.relyingPartyRegistrationResolver);
		given(this.relyingPartyRegistrationResolver.convert(any(HttpServletRequest.class)))
				.willReturn(this.relyingPartyRegistration);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("SAMLResponse", Saml2Utils.samlEncode("response".getBytes(StandardCharsets.UTF_8)));
		Saml2AuthenticationToken token = converter.convert(request);
		assertThat(token.getSaml2Response()).isEqualTo("response");
		assertThat(token.getRelyingPartyRegistration().getRegistrationId())
				.isEqualTo(this.relyingPartyRegistration.getRegistrationId());
	}

	@Test
	public void convertWhenNoSamlResponseThenNull() {
		Saml2AuthenticationTokenConverter converter = new Saml2AuthenticationTokenConverter(
				this.relyingPartyRegistrationResolver);
		given(this.relyingPartyRegistrationResolver.convert(any(HttpServletRequest.class)))
				.willReturn(this.relyingPartyRegistration);
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertThat(converter.convert(request)).isNull();
	}

	@Test
	public void convertWhenNoRelyingPartyRegistrationThenNull() {
		Saml2AuthenticationTokenConverter converter = new Saml2AuthenticationTokenConverter(
				this.relyingPartyRegistrationResolver);
		given(this.relyingPartyRegistrationResolver.convert(any(HttpServletRequest.class))).willReturn(null);
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertThat(converter.convert(request)).isNull();
	}

	@Test
	public void convertWhenGetRequestThenInflates() {
		Saml2AuthenticationTokenConverter converter = new Saml2AuthenticationTokenConverter(
				this.relyingPartyRegistrationResolver);
		given(this.relyingPartyRegistrationResolver.convert(any(HttpServletRequest.class)))
				.willReturn(this.relyingPartyRegistration);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		byte[] deflated = Saml2Utils.samlDeflate("response");
		String encoded = Saml2Utils.samlEncode(deflated);
		request.setParameter("SAMLResponse", encoded);
		Saml2AuthenticationToken token = converter.convert(request);
		assertThat(token.getSaml2Response()).isEqualTo("response");
		assertThat(token.getRelyingPartyRegistration().getRegistrationId())
				.isEqualTo(this.relyingPartyRegistration.getRegistrationId());
	}

	@Test
	public void constructorWhenResolverIsNullThenIllegalArgument() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Saml2AuthenticationTokenConverter(null));
	}

	@Test
	public void convertWhenUsingSamlUtilsBase64ThenXmlIsValid() throws Exception {
		Saml2AuthenticationTokenConverter converter = new Saml2AuthenticationTokenConverter(
				this.relyingPartyRegistrationResolver);
		given(this.relyingPartyRegistrationResolver.convert(any(HttpServletRequest.class)))
				.willReturn(this.relyingPartyRegistration);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("SAMLResponse", getSsoCircleEncodedXml());
		Saml2AuthenticationToken token = converter.convert(request);
		validateSsoCircleXml(token.getSaml2Response());
	}

	private void validateSsoCircleXml(String xml) {
		assertThat(xml).contains("InResponseTo=\"ARQ9a73ead-7dcf-45a8-89eb-26f3c9900c36\"")
				.contains(" ID=\"s246d157446618e90e43fb79bdd4d9e9e19cf2c7c4\"")
				.contains("<saml:Issuer>https://idp.ssocircle.com</saml:Issuer>");
	}

	private String getSsoCircleEncodedXml() throws IOException {
		ClassPathResource resource = new ClassPathResource("saml2-response-sso-circle.encoded");
		String response = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
		return UriUtils.decode(response, StandardCharsets.UTF_8);
	}

}
