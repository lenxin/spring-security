package org.springframework.security.oauth2.client;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;

/**
 * Handles when an OAuth 2.0 Client has been successfully authorized (or re-authorized)
 * via the authorization server.
 *
 * @author Phil Clay
 * @since 5.3
 */
@FunctionalInterface
public interface ReactiveOAuth2AuthorizationSuccessHandler {

	/**
	 * Called when an OAuth 2.0 Client has been successfully authorized (or re-authorized)
	 * via the authorization server.
	 * @param authorizedClient the client that was successfully authorized
	 * @param principal the {@code Principal} associated with the authorized client
	 * @param attributes an immutable {@code Map} of extra optional attributes present
	 * under certain conditions. For example, this might contain a
	 * {@link org.springframework.web.server.ServerWebExchange ServerWebExchange} if the
	 * authorization was performed within the context of a {@code ServerWebExchange}.
	 * @return an empty {@link Mono} that completes after this handler has finished
	 * handling the event.
	 */
	Mono<Void> onAuthorizationSuccess(OAuth2AuthorizedClient authorizedClient, Authentication principal,
			Map<String, Object> attributes);

}
