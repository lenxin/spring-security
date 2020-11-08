package org.springframework.security.oauth2.client;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;

/**
 * Handles when an OAuth 2.0 Client fails to authorize (or re-authorize) via the
 * Authorization Server or Resource Server.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see OAuth2AuthorizedClient
 * @see OAuth2AuthorizedClientManager
 */
@FunctionalInterface
public interface OAuth2AuthorizationFailureHandler {

	/**
	 * Called when an OAuth 2.0 Client fails to authorize (or re-authorize) via the
	 * Authorization Server or Resource Server.
	 * @param authorizationException the exception that contains details about what failed
	 * @param principal the {@code Principal} associated with the attempted authorization
	 * @param attributes an immutable {@code Map} of (optional) attributes present under
	 * certain conditions. For example, this might contain a
	 * {@code javax.servlet.http.HttpServletRequest} and
	 * {@code javax.servlet.http.HttpServletResponse} if the authorization was performed
	 * within the context of a {@code javax.servlet.ServletContext}.
	 */
	void onAuthorizationFailure(OAuth2AuthorizationException authorizationException, Authentication principal,
			Map<String, Object> attributes);

}
