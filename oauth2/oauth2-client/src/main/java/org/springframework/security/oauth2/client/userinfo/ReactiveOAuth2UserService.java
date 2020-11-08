package org.springframework.security.oauth2.client.userinfo;

import reactor.core.publisher.Mono;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Implementations of this interface are responsible for obtaining the user attributes of
 * the End-User (Resource Owner) from the UserInfo Endpoint using the
 * {@link OAuth2UserRequest#getAccessToken() Access Token} granted to the
 * {@link OAuth2UserRequest#getClientRegistration() Client} and returning an
 * {@link AuthenticatedPrincipal} in the form of an {@link OAuth2User}.
 *
 * @author Rob Winch
 * @since 5.1
 * @see OAuth2UserRequest
 * @see OAuth2User
 * @see AuthenticatedPrincipal
 * @param <R> The type of OAuth 2.0 User Request
 * @param <U> The type of OAuth 2.0 User
 */
@FunctionalInterface
public interface ReactiveOAuth2UserService<R extends OAuth2UserRequest, U extends OAuth2User> {

	/**
	 * Returns an {@link OAuth2User} after obtaining the user attributes of the End-User
	 * from the UserInfo Endpoint.
	 * @param userRequest the user request
	 * @return an {@link OAuth2User}
	 * @throws OAuth2AuthenticationException if an error occurs while attempting to obtain
	 * the user attributes from the UserInfo Endpoint
	 */
	Mono<U> loadUser(R userRequest) throws OAuth2AuthenticationException;

}
