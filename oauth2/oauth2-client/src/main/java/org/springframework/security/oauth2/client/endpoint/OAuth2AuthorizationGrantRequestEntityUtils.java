package org.springframework.security.oauth2.client.endpoint;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * Utility methods used by the {@link Converter}'s that convert from an implementation of
 * an {@link AbstractOAuth2AuthorizationGrantRequest} to a {@link RequestEntity}
 * representation of an OAuth 2.0 Access Token Request for the specific Authorization
 * Grant.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see OAuth2AuthorizationCodeGrantRequestEntityConverter
 * @see OAuth2ClientCredentialsGrantRequestEntityConverter
 */
final class OAuth2AuthorizationGrantRequestEntityUtils {

	private static HttpHeaders DEFAULT_TOKEN_REQUEST_HEADERS = getDefaultTokenRequestHeaders();

	private OAuth2AuthorizationGrantRequestEntityUtils() {
	}

	static HttpHeaders getTokenRequestHeaders(ClientRegistration clientRegistration) {
		HttpHeaders headers = new HttpHeaders();
		headers.addAll(DEFAULT_TOKEN_REQUEST_HEADERS);
		if (ClientAuthenticationMethod.BASIC.equals(clientRegistration.getClientAuthenticationMethod())) {
			headers.setBasicAuth(clientRegistration.getClientId(), clientRegistration.getClientSecret());
		}
		return headers;
	}

	private static HttpHeaders getDefaultTokenRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		final MediaType contentType = MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		headers.setContentType(contentType);
		return headers;
	}

}
