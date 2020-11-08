package org.springframework.security.oauth2.client;

import reactor.core.publisher.Mono;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;

/**
 * Implementations of this interface are responsible for the overall management of
 * {@link OAuth2AuthorizedClient Authorized Client(s)}.
 *
 * <p>
 * The primary responsibilities include:
 * <ol>
 * <li>Authorizing (or re-authorizing) an OAuth 2.0 Client by leveraging a
 * {@link ReactiveOAuth2AuthorizedClientProvider}(s).</li>
 * <li>Delegating the persistence of an {@link OAuth2AuthorizedClient}, typically using a
 * {@link ReactiveOAuth2AuthorizedClientService} OR
 * {@link ServerOAuth2AuthorizedClientRepository}.</li>
 * </ol>
 *
 * @author Joe Grandja
 * @since 5.2
 * @see OAuth2AuthorizedClient
 * @see ReactiveOAuth2AuthorizedClientProvider
 * @see ReactiveOAuth2AuthorizedClientService
 * @see ServerOAuth2AuthorizedClientRepository
 */
@FunctionalInterface
public interface ReactiveOAuth2AuthorizedClientManager {

	/**
	 * Attempt to authorize or re-authorize (if required) the {@link ClientRegistration
	 * client} identified by the provided
	 * {@link OAuth2AuthorizeRequest#getClientRegistrationId() clientRegistrationId}.
	 * Implementations must return an empty {@code Mono} if authorization is not supported
	 * for the specified client, e.g. the associated
	 * {@link ReactiveOAuth2AuthorizedClientProvider}(s) does not support the
	 * {@link ClientRegistration#getAuthorizationGrantType() authorization grant} type
	 * configured for the client.
	 *
	 * <p>
	 * In the case of re-authorization, implementations must return the provided
	 * {@link OAuth2AuthorizeRequest#getAuthorizedClient() authorized client} if
	 * re-authorization is not supported for the client OR is not required, e.g. a
	 * {@link OAuth2AuthorizedClient#getRefreshToken() refresh token} is not available OR
	 * the {@link OAuth2AuthorizedClient#getAccessToken() access token} is not expired.
	 * @param authorizeRequest the authorize request
	 * @return the {@link OAuth2AuthorizedClient} or an empty {@code Mono} if
	 * authorization is not supported for the specified client
	 */
	Mono<OAuth2AuthorizedClient> authorize(OAuth2AuthorizeRequest authorizeRequest);

}
