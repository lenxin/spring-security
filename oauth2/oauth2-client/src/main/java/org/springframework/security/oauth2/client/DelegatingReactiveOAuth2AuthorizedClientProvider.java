package org.springframework.security.oauth2.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.util.Assert;

/**
 * An implementation of a {@link ReactiveOAuth2AuthorizedClientProvider} that simply
 * delegates to it's internal {@code List} of
 * {@link ReactiveOAuth2AuthorizedClientProvider}(s).
 * <p>
 * Each provider is given a chance to
 * {@link ReactiveOAuth2AuthorizedClientProvider#authorize(OAuth2AuthorizationContext)
 * authorize} the {@link OAuth2AuthorizationContext#getClientRegistration() client} in the
 * provided context with the first available {@link OAuth2AuthorizedClient} being
 * returned.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see ReactiveOAuth2AuthorizedClientProvider
 */
public final class DelegatingReactiveOAuth2AuthorizedClientProvider implements ReactiveOAuth2AuthorizedClientProvider {

	private final List<ReactiveOAuth2AuthorizedClientProvider> authorizedClientProviders;

	/**
	 * Constructs a {@code DelegatingReactiveOAuth2AuthorizedClientProvider} using the
	 * provided parameters.
	 * @param authorizedClientProviders a list of
	 * {@link ReactiveOAuth2AuthorizedClientProvider}(s)
	 */
	public DelegatingReactiveOAuth2AuthorizedClientProvider(
			ReactiveOAuth2AuthorizedClientProvider... authorizedClientProviders) {
		Assert.notEmpty(authorizedClientProviders, "authorizedClientProviders cannot be empty");
		this.authorizedClientProviders = Collections.unmodifiableList(Arrays.asList(authorizedClientProviders));
	}

	/**
	 * Constructs a {@code DelegatingReactiveOAuth2AuthorizedClientProvider} using the
	 * provided parameters.
	 * @param authorizedClientProviders a {@code List} of
	 * {@link OAuth2AuthorizedClientProvider}(s)
	 */
	public DelegatingReactiveOAuth2AuthorizedClientProvider(
			List<ReactiveOAuth2AuthorizedClientProvider> authorizedClientProviders) {
		Assert.notEmpty(authorizedClientProviders, "authorizedClientProviders cannot be empty");
		this.authorizedClientProviders = Collections.unmodifiableList(new ArrayList<>(authorizedClientProviders));
	}

	@Override
	public Mono<OAuth2AuthorizedClient> authorize(OAuth2AuthorizationContext context) {
		Assert.notNull(context, "context cannot be null");
		return Flux.fromIterable(this.authorizedClientProviders)
				.concatMap((authorizedClientProvider) -> authorizedClientProvider.authorize(context)).next();
	}

}
