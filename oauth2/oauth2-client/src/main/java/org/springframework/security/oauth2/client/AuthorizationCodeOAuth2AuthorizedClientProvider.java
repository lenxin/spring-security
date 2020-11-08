package org.springframework.security.oauth2.client;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

/**
 * An implementation of an {@link OAuth2AuthorizedClientProvider} for the
 * {@link AuthorizationGrantType#AUTHORIZATION_CODE authorization_code} grant.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see OAuth2AuthorizedClientProvider
 */
public final class AuthorizationCodeOAuth2AuthorizedClientProvider implements OAuth2AuthorizedClientProvider {

	/**
	 * Attempt to authorize the {@link OAuth2AuthorizationContext#getClientRegistration()
	 * client} in the provided {@code context}. Returns {@code null} if authorization is
	 * not supported, e.g. the client's
	 * {@link ClientRegistration#getAuthorizationGrantType() authorization grant type} is
	 * not {@link AuthorizationGrantType#AUTHORIZATION_CODE authorization_code} OR the
	 * client is already authorized.
	 * @param context the context that holds authorization-specific state for the client
	 * @return the {@link OAuth2AuthorizedClient} or {@code null} if authorization is not
	 * supported
	 */
	@Override
	@Nullable
	public OAuth2AuthorizedClient authorize(OAuth2AuthorizationContext context) {
		Assert.notNull(context, "context cannot be null");
		if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(
				context.getClientRegistration().getAuthorizationGrantType()) && context.getAuthorizedClient() == null) {
			// ClientAuthorizationRequiredException is caught by
			// OAuth2AuthorizationRequestRedirectFilter which initiates authorization
			throw new ClientAuthorizationRequiredException(context.getClientRegistration().getRegistrationId());
		}
		return null;
	}

}
