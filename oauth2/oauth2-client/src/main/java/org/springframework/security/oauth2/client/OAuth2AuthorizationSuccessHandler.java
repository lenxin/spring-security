package org.springframework.security.oauth2.client;

import java.util.Map;

import org.springframework.security.core.Authentication;

/**
 * Handles when an OAuth 2.0 Client has been successfully authorized (or re-authorized)
 * via the Authorization Server.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see OAuth2AuthorizedClient
 * @see OAuth2AuthorizedClientManager
 */
@FunctionalInterface
public interface OAuth2AuthorizationSuccessHandler {

	/**
	 * Called when an OAuth 2.0 Client has been successfully authorized (or re-authorized)
	 * via the Authorization Server.
	 * @param authorizedClient the client that was successfully authorized (or
	 * re-authorized)
	 * @param principal the {@code Principal} associated with the authorized client
	 * @param attributes an immutable {@code Map} of (optional) attributes present under
	 * certain conditions. For example, this might contain a
	 * {@code javax.servlet.http.HttpServletRequest} and
	 * {@code javax.servlet.http.HttpServletResponse} if the authorization was performed
	 * within the context of a {@code javax.servlet.ServletContext}.
	 */
	void onAuthorizationSuccess(OAuth2AuthorizedClient authorizedClient, Authentication principal,
			Map<String, Object> attributes);

}
