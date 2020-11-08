package org.springframework.security.oauth2.client.web.server;

import reactor.core.publisher.Mono;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * Implementations of this interface are capable of resolving an
 * {@link OAuth2AuthorizationRequest} from the provided {@code ServerWebExchange}. Used by
 * the {@link OAuth2AuthorizationRequestRedirectWebFilter} for resolving Authorization
 * Requests.
 *
 * @author Rob Winch
 * @since 5.1
 * @see OAuth2AuthorizationRequest
 * @see OAuth2AuthorizationRequestRedirectWebFilter
 */
public interface ServerOAuth2AuthorizationRequestResolver {

	/**
	 * Returns the {@link OAuth2AuthorizationRequest} resolved from the provided
	 * {@code HttpServletRequest} or {@code null} if not available.
	 * @param exchange the {@code ServerWebExchange}
	 * @return the resolved {@link OAuth2AuthorizationRequest} or {@code null} if not
	 * available
	 */
	Mono<OAuth2AuthorizationRequest> resolve(ServerWebExchange exchange);

	/**
	 * Returns the {@link OAuth2AuthorizationRequest} resolved from the provided
	 * {@code HttpServletRequest} or {@code null} if not available.
	 * @param exchange the {@code ServerWebExchange}
	 * @param clientRegistrationId the client registration id
	 * @return the resolved {@link OAuth2AuthorizationRequest} or {@code null} if not
	 * available
	 */
	Mono<OAuth2AuthorizationRequest> resolve(ServerWebExchange exchange, String clientRegistrationId);

}
