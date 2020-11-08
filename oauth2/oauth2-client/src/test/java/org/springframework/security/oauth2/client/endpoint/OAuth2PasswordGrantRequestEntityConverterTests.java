package org.springframework.security.oauth2.client.endpoint;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.TestClientRegistrations;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OAuth2PasswordGrantRequestEntityConverter}.
 *
 * @author Joe Grandja
 */
public class OAuth2PasswordGrantRequestEntityConverterTests {

	private OAuth2PasswordGrantRequestEntityConverter converter = new OAuth2PasswordGrantRequestEntityConverter();

	private OAuth2PasswordGrantRequest passwordGrantRequest;

	@Before
	public void setup() {
		// @formatter:off
		ClientRegistration clientRegistration = TestClientRegistrations.clientRegistration()
				.authorizationGrantType(AuthorizationGrantType.PASSWORD)
				.scope("read", "write")
				.build();
		// @formatter:on
		this.passwordGrantRequest = new OAuth2PasswordGrantRequest(clientRegistration, "user1", "password");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void convertWhenGrantRequestValidThenConverts() {
		RequestEntity<?> requestEntity = this.converter.convert(this.passwordGrantRequest);
		ClientRegistration clientRegistration = this.passwordGrantRequest.getClientRegistration();
		assertThat(requestEntity.getMethod()).isEqualTo(HttpMethod.POST);
		assertThat(requestEntity.getUrl().toASCIIString())
				.isEqualTo(clientRegistration.getProviderDetails().getTokenUri());
		HttpHeaders headers = requestEntity.getHeaders();
		assertThat(headers.getAccept()).contains(MediaType.APPLICATION_JSON_UTF8);
		assertThat(headers.getContentType())
				.isEqualTo(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8"));
		assertThat(headers.getFirst(HttpHeaders.AUTHORIZATION)).startsWith("Basic ");
		MultiValueMap<String, String> formParameters = (MultiValueMap<String, String>) requestEntity.getBody();
		assertThat(formParameters.getFirst(OAuth2ParameterNames.GRANT_TYPE))
				.isEqualTo(AuthorizationGrantType.PASSWORD.getValue());
		assertThat(formParameters.getFirst(OAuth2ParameterNames.USERNAME)).isEqualTo("user1");
		assertThat(formParameters.getFirst(OAuth2ParameterNames.PASSWORD)).isEqualTo("password");
		assertThat(formParameters.getFirst(OAuth2ParameterNames.SCOPE)).isEqualTo("read write");
	}

}
