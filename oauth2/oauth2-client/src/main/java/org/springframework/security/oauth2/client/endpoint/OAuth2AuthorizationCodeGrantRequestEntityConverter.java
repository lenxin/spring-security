package org.springframework.security.oauth2.client.endpoint;

import java.net.URI;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * A {@link Converter} that converts the provided
 * {@link OAuth2AuthorizationCodeGrantRequest} to a {@link RequestEntity} representation
 * of an OAuth 2.0 Access Token Request for the Authorization Code Grant.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see Converter
 * @see OAuth2AuthorizationCodeGrantRequest
 * @see RequestEntity
 */
public class OAuth2AuthorizationCodeGrantRequestEntityConverter
		implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

	/**
	 * Returns the {@link RequestEntity} used for the Access Token Request.
	 * @param authorizationCodeGrantRequest the authorization code grant request
	 * @return the {@link RequestEntity} used for the Access Token Request
	 */
	@Override
	public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
		ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
		HttpHeaders headers = OAuth2AuthorizationGrantRequestEntityUtils.getTokenRequestHeaders(clientRegistration);
		MultiValueMap<String, String> formParameters = this.buildFormParameters(authorizationCodeGrantRequest);
		URI uri = UriComponentsBuilder.fromUriString(clientRegistration.getProviderDetails().getTokenUri()).build()
				.toUri();
		return new RequestEntity<>(formParameters, headers, HttpMethod.POST, uri);
	}

	/**
	 * Returns a {@link MultiValueMap} of the form parameters used for the Access Token
	 * Request body.
	 * @param authorizationCodeGrantRequest the authorization code grant request
	 * @return a {@link MultiValueMap} of the form parameters used for the Access Token
	 * Request body
	 */
	private MultiValueMap<String, String> buildFormParameters(
			OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
		ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
		OAuth2AuthorizationExchange authorizationExchange = authorizationCodeGrantRequest.getAuthorizationExchange();
		MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
		formParameters.add(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
		formParameters.add(OAuth2ParameterNames.CODE, authorizationExchange.getAuthorizationResponse().getCode());
		String redirectUri = authorizationExchange.getAuthorizationRequest().getRedirectUri();
		String codeVerifier = authorizationExchange.getAuthorizationRequest()
				.getAttribute(PkceParameterNames.CODE_VERIFIER);
		if (redirectUri != null) {
			formParameters.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
		}
		if (!ClientAuthenticationMethod.BASIC.equals(clientRegistration.getClientAuthenticationMethod())) {
			formParameters.add(OAuth2ParameterNames.CLIENT_ID, clientRegistration.getClientId());
		}
		if (ClientAuthenticationMethod.POST.equals(clientRegistration.getClientAuthenticationMethod())) {
			formParameters.add(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.getClientSecret());
		}
		if (codeVerifier != null) {
			formParameters.add(PkceParameterNames.CODE_VERIFIER, codeVerifier);
		}
		return formParameters;
	}

}
