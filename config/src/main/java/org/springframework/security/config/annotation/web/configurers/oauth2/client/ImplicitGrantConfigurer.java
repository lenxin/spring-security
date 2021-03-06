package org.springframework.security.config.annotation.web.configurers.oauth2.client;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.util.Assert;

/**
 * An {@link AbstractHttpConfigurer} for the OAuth 2.0 Implicit Grant type.
 *
 * <h2>Security Filters</h2>
 *
 * The following {@code Filter}'s are populated:
 *
 * <ul>
 * <li>{@link OAuth2AuthorizationRequestRedirectFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * The following shared objects are populated:
 *
 * <ul>
 * <li>{@link ClientRegistrationRepository} (required)</li>
 * </ul>
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>{@link ClientRegistrationRepository}</li>
 * </ul>
 *
 * @deprecated It is not recommended to use the implicit flow due to the inherent risks of
 * returning access tokens in an HTTP redirect without any confirmation that it has been
 * received by the client. See reference
 * <a target="_blank" href="https://oauth.net/2/grant-types/implicit/">OAuth 2.0 Implicit
 * Grant</a>.
 * @author Joe Grandja
 * @since 5.0
 * @see OAuth2AuthorizationRequestRedirectFilter
 * @see ClientRegistrationRepository
 */
@Deprecated
public final class ImplicitGrantConfigurer<B extends HttpSecurityBuilder<B>>
		extends AbstractHttpConfigurer<ImplicitGrantConfigurer<B>, B> {

	private String authorizationRequestBaseUri;

	/**
	 * Sets the base {@code URI} used for authorization requests.
	 * @param authorizationRequestBaseUri the base {@code URI} used for authorization
	 * requests
	 * @return the {@link ImplicitGrantConfigurer} for further configuration
	 */
	public ImplicitGrantConfigurer<B> authorizationRequestBaseUri(String authorizationRequestBaseUri) {
		Assert.hasText(authorizationRequestBaseUri, "authorizationRequestBaseUri cannot be empty");
		this.authorizationRequestBaseUri = authorizationRequestBaseUri;
		return this;
	}

	/**
	 * Sets the repository of client registrations.
	 * @param clientRegistrationRepository the repository of client registrations
	 * @return the {@link ImplicitGrantConfigurer} for further configuration
	 */
	public ImplicitGrantConfigurer<B> clientRegistrationRepository(
			ClientRegistrationRepository clientRegistrationRepository) {
		Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
		this.getBuilder().setSharedObject(ClientRegistrationRepository.class, clientRegistrationRepository);
		return this;
	}

	@Override
	public void configure(B http) {
		OAuth2AuthorizationRequestRedirectFilter authorizationRequestFilter = new OAuth2AuthorizationRequestRedirectFilter(
				OAuth2ClientConfigurerUtils.getClientRegistrationRepository(this.getBuilder()),
				this.getAuthorizationRequestBaseUri());
		http.addFilter(this.postProcess(authorizationRequestFilter));
	}

	private String getAuthorizationRequestBaseUri() {
		return (this.authorizationRequestBaseUri != null) ? this.authorizationRequestBaseUri
				: OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
	}

}
