package org.springframework.security.oauth2.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.util.Assert;

/**
 * An {@link OAuth2AuthorizedClientService} that stores {@link OAuth2AuthorizedClient
 * Authorized Client(s)} in-memory.
 *
 * @author Rob Winch
 * @author Vedran Pavic
 * @since 5.1
 * @see OAuth2AuthorizedClientService
 * @see OAuth2AuthorizedClient
 * @see ClientRegistration
 * @see Authentication
 */
public final class InMemoryReactiveOAuth2AuthorizedClientService implements ReactiveOAuth2AuthorizedClientService {

	private final Map<OAuth2AuthorizedClientId, OAuth2AuthorizedClient> authorizedClients = new ConcurrentHashMap<>();

	private final ReactiveClientRegistrationRepository clientRegistrationRepository;

	/**
	 * Constructs an {@code InMemoryReactiveOAuth2AuthorizedClientService} using the
	 * provided parameters.
	 * @param clientRegistrationRepository the repository of client registrations
	 */
	public InMemoryReactiveOAuth2AuthorizedClientService(
			ReactiveClientRegistrationRepository clientRegistrationRepository) {
		Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
		this.clientRegistrationRepository = clientRegistrationRepository;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends OAuth2AuthorizedClient> Mono<T> loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
		Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		return (Mono<T>) this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId)
				.map((clientRegistration) -> new OAuth2AuthorizedClientId(clientRegistrationId, principalName))
				.flatMap((identifier) -> Mono.justOrEmpty(this.authorizedClients.get(identifier)));
	}

	@Override
	public Mono<Void> saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
		Assert.notNull(authorizedClient, "authorizedClient cannot be null");
		Assert.notNull(principal, "principal cannot be null");
		return Mono.fromRunnable(() -> {
			OAuth2AuthorizedClientId identifier = new OAuth2AuthorizedClientId(
					authorizedClient.getClientRegistration().getRegistrationId(), principal.getName());
			this.authorizedClients.put(identifier, authorizedClient);
		});
	}

	@Override
	public Mono<Void> removeAuthorizedClient(String clientRegistrationId, String principalName) {
		Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		// @formatter:off
		return this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId)
				.map((clientRegistration) -> new OAuth2AuthorizedClientId(clientRegistrationId, principalName))
				.doOnNext(this.authorizedClients::remove)
				.then(Mono.empty());
		// @formatter:on
	}

}
