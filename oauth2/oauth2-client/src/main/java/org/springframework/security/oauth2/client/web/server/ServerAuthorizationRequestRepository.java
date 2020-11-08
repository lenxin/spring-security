package org.springframework.security.oauth2.client.web.server;

import reactor.core.publisher.Mono;

import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * Implementations of this interface are responsible for the persistence of
 * {@link OAuth2AuthorizationRequest} between requests.
 *
 * <p>
 * Used by the {@link OAuth2AuthorizationRequestRedirectFilter} for persisting the
 * Authorization Request before it initiates the authorization code grant flow. As well,
 * used by the {@link OAuth2LoginAuthenticationFilter} for resolving the associated
 * Authorization Request when handling the callback of the Authorization Response.
 *
 * @param <T> The type of OAuth 2.0 Authorization Request
 * @author Rob Winch
 * @since 5.1
 * @see OAuth2AuthorizationRequest
 * @see HttpSessionOAuth2AuthorizationRequestRepository
 */
public interface ServerAuthorizationRequestRepository<T extends OAuth2AuthorizationRequest> {

	/**
	 * Returns the {@link OAuth2AuthorizationRequest} associated to the provided
	 * {@code HttpServletRequest} or {@code null} if not available.
	 * @param exchange the {@code ServerWebExchange}
	 * @return the {@link OAuth2AuthorizationRequest} or {@code null} if not available
	 */
	Mono<T> loadAuthorizationRequest(ServerWebExchange exchange);

	/**
	 * Persists the {@link OAuth2AuthorizationRequest} associating it to the provided
	 * {@code HttpServletRequest} and/or {@code HttpServletResponse}.
	 * @param authorizationRequest the {@link OAuth2AuthorizationRequest}
	 * @param exchange the {@code ServerWebExchange}
	 */
	Mono<Void> saveAuthorizationRequest(T authorizationRequest, ServerWebExchange exchange);

	/**
	 * Removes and returns the {@link OAuth2AuthorizationRequest} associated to the
	 * provided {@code HttpServletRequest} or if not available returns {@code null}.
	 * @param exchange the {@code ServerWebExchange}
	 * @return the removed {@link OAuth2AuthorizationRequest} or {@code null} if not
	 * available
	 */
	Mono<T> removeAuthorizationRequest(ServerWebExchange exchange);

}
