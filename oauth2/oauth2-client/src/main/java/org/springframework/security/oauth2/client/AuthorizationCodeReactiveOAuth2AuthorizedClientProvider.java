package org.springframework.security.oauth2.client;

import reactor.core.publisher.Mono;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

/**
 * An implementation of a {@link ReactiveOAuth2AuthorizedClientProvider} for the
 * {@link AuthorizationGrantType#AUTHORIZATION_CODE authorization_code} grant.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see ReactiveOAuth2AuthorizedClientProvider
 */
public final class AuthorizationCodeReactiveOAuth2AuthorizedClientProvider
		implements ReactiveOAuth2AuthorizedClientProvider {

	/**
	 * Attempt to authorize the {@link OAuth2AuthorizationContext#getClientRegistration()
	 * client} in the provided {@code context}. Returns an empty {@code Mono} if
	 * authorization is not supported, e.g. the client's
	 * {@link ClientRegistration#getAuthorizationGrantType() authorization grant type} is
	 * not {@link AuthorizationGrantType#AUTHORIZATION_CODE authorization_code} OR the
	 * client is already authorized.
	 * @param context the context that holds authorization-specific state for the client
	 * @return the {@link OAuth2AuthorizedClient} or an empty {@code Mono} if
	 * authorization is not supported
	 */
	@Override
	public Mono<OAuth2AuthorizedClient> authorize(OAuth2AuthorizationContext context) {
		Assert.notNull(context, "context cannot be null");
		if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(
				context.getClientRegistration().getAuthorizationGrantType()) && context.getAuthorizedClient() == null) {
			// ClientAuthorizationRequiredException is caught by
			// OAuth2AuthorizationRequestRedirectWebFilter which initiates authorization
			return Mono.error(() -> new ClientAuthorizationRequiredException(
					context.getClientRegistration().getRegistrationId()));
		}
		return Mono.empty();
	}

}
