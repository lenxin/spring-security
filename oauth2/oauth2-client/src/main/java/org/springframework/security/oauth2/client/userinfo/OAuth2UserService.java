package org.springframework.security.oauth2.client.userinfo;

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
 * @author Joe Grandja
 * @since 5.0
 * @see OAuth2UserRequest
 * @see OAuth2User
 * @see AuthenticatedPrincipal
 * @param <R> The type of OAuth 2.0 User Request
 * @param <U> The type of OAuth 2.0 User
 */
@FunctionalInterface
public interface OAuth2UserService<R extends OAuth2UserRequest, U extends OAuth2User> {

	/**
	 * Returns an {@link OAuth2User} after obtaining the user attributes of the End-User
	 * from the UserInfo Endpoint.
	 * @param userRequest the user request
	 * @return an {@link OAuth2User}
	 * @throws OAuth2AuthenticationException if an error occurs while attempting to obtain
	 * the user attributes from the UserInfo Endpoint
	 */
	U loadUser(R userRequest) throws OAuth2AuthenticationException;

}
