package org.springframework.security.oauth2.client;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * A strategy for authorizing (or re-authorizing) an OAuth 2.0 Client. Implementations
 * will typically implement a specific {@link AuthorizationGrantType authorization grant}
 * type.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see OAuth2AuthorizedClient
 * @see OAuth2AuthorizationContext
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-1.3">Section
 * 1.3 Authorization Grant</a>
 */
@FunctionalInterface
public interface OAuth2AuthorizedClientProvider {

	/**
	 * Attempt to authorize (or re-authorize) the
	 * {@link OAuth2AuthorizationContext#getClientRegistration() client} in the provided
	 * context. Implementations must return {@code null} if authorization is not supported
	 * for the specified client, e.g. the provider doesn't support the
	 * {@link ClientRegistration#getAuthorizationGrantType() authorization grant} type
	 * configured for the client.
	 * @param context the context that holds authorization-specific state for the client
	 * @return the {@link OAuth2AuthorizedClient} or {@code null} if authorization is not
	 * supported for the specified client
	 */
	@Nullable
	OAuth2AuthorizedClient authorize(OAuth2AuthorizationContext context);

}
