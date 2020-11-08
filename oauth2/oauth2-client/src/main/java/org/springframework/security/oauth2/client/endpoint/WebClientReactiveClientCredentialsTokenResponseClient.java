package org.springframework.security.oauth2.client.endpoint;

import java.util.Set;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

/**
 * An implementation of a {@link ReactiveOAuth2AccessTokenResponseClient} that
 * &quot;exchanges&quot; a client credential for an access token credential at the
 * Authorization Server's Token Endpoint.
 *
 * @author Rob Winch
 * @since 5.1
 * @see ReactiveOAuth2AccessTokenResponseClient
 * @see OAuth2AuthorizationCodeGrantRequest
 * @see OAuth2AccessTokenResponse
 * @see <a target="_blank" href=
 * "https://connect2id.com/products/nimbus-oauth-openid-connect-sdk">Nimbus OAuth 2.0
 * SDK</a>
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-4.1.3">Section 4.1.3 Access Token Request
 * (Authorization Code Grant)</a>
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-4.1.4">Section 4.1.4 Access Token Response
 * (Authorization Code Grant)</a>
 */
public class WebClientReactiveClientCredentialsTokenResponseClient
		extends AbstractWebClientReactiveOAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> {

	@Override
	ClientRegistration clientRegistration(OAuth2ClientCredentialsGrantRequest grantRequest) {
		return grantRequest.getClientRegistration();
	}

	@Override
	Set<String> scopes(OAuth2ClientCredentialsGrantRequest grantRequest) {
		return grantRequest.getClientRegistration().getScopes();
	}

}
