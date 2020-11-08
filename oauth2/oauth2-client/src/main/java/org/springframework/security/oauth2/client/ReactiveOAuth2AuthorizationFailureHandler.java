package org.springframework.security.oauth2.client;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;

/**
 * Handles when an OAuth 2.0 Client fails to authorize (or re-authorize) via the
 * authorization server or resource server.
 *
 * @author Phil Clay
 * @since 5.3
 */
@FunctionalInterface
public interface ReactiveOAuth2AuthorizationFailureHandler {

	/**
	 * Called when an OAuth 2.0 Client fails to authorize (or re-authorize) via the
	 * authorization server or resource server.
	 * @param authorizationException the exception that contains details about what failed
	 * @param principal the {@code Principal} that was attempted to be authorized
	 * @param attributes an immutable {@code Map} of extra optional attributes present
	 * under certain conditions. For example, this might contain a
	 * {@link org.springframework.web.server.ServerWebExchange ServerWebExchange} if the
	 * authorization was performed within the context of a {@code ServerWebExchange}.
	 * @return an empty {@link Mono} that completes after this handler has finished
	 * handling the event.
	 */
	Mono<Void> onAuthorizationFailure(OAuth2AuthorizationException authorizationException, Authentication principal,
			Map<String, Object> attributes);

}
