package org.springframework.security.oauth2.client.endpoint;

import java.util.Set;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * An implementation of a {@link ReactiveOAuth2AccessTokenResponseClient} for the
 * {@link AuthorizationGrantType#PASSWORD password} grant. This implementation uses
 * {@link WebClient} when requesting an access token credential at the Authorization
 * Server's Token Endpoint.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see ReactiveOAuth2AccessTokenResponseClient
 * @see OAuth2PasswordGrantRequest
 * @see OAuth2AccessTokenResponse
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-4.3.2">Section 4.3.2 Access Token Request
 * (Resource Owner Password Credentials Grant)</a>
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-4.3.3">Section 4.3.3 Access Token Response
 * (Resource Owner Password Credentials Grant)</a>
 */
public final class WebClientReactivePasswordTokenResponseClient
		extends AbstractWebClientReactiveOAuth2AccessTokenResponseClient<OAuth2PasswordGrantRequest> {

	@Override
	ClientRegistration clientRegistration(OAuth2PasswordGrantRequest grantRequest) {
		return grantRequest.getClientRegistration();
	}

	@Override
	Set<String> scopes(OAuth2PasswordGrantRequest grantRequest) {
		return grantRequest.getClientRegistration().getScopes();
	}

	@Override
	BodyInserters.FormInserter<String> populateTokenRequestBody(OAuth2PasswordGrantRequest grantRequest,
			BodyInserters.FormInserter<String> body) {
		return super.populateTokenRequestBody(grantRequest, body)
				.with(OAuth2ParameterNames.USERNAME, grantRequest.getUsername())
				.with(OAuth2ParameterNames.PASSWORD, grantRequest.getPassword());
	}

}
