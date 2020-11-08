package org.springframework.security.oauth2.client.registration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.publisher.Mono;

import org.springframework.util.Assert;

/**
 * A Reactive {@link ClientRegistrationRepository} that stores
 * {@link ClientRegistration}(s) in-memory.
 *
 * @author Rob Winch
 * @author Ebert Toribio
 * @since 5.1
 * @see ClientRegistrationRepository
 * @see ClientRegistration
 */
public final class InMemoryReactiveClientRegistrationRepository
		implements ReactiveClientRegistrationRepository, Iterable<ClientRegistration> {

	private final Map<String, ClientRegistration> clientIdToClientRegistration;

	/**
	 * Constructs an {@code InMemoryReactiveClientRegistrationRepository} using the
	 * provided parameters.
	 * @param registrations the client registration(s)
	 */
	public InMemoryReactiveClientRegistrationRepository(ClientRegistration... registrations) {
		this(toList(registrations));
	}

	private static List<ClientRegistration> toList(ClientRegistration... registrations) {
		Assert.notEmpty(registrations, "registrations cannot be null or empty");
		return Arrays.asList(registrations);
	}

	/**
	 * Constructs an {@code InMemoryReactiveClientRegistrationRepository} using the
	 * provided parameters.
	 * @param registrations the client registration(s)
	 */
	public InMemoryReactiveClientRegistrationRepository(List<ClientRegistration> registrations) {
		this.clientIdToClientRegistration = toUnmodifiableConcurrentMap(registrations);
	}

	@Override
	public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
		return Mono.justOrEmpty(this.clientIdToClientRegistration.get(registrationId));
	}

	/**
	 * Returns an {@code Iterator} of {@link ClientRegistration}.
	 * @return an {@code Iterator<ClientRegistration>}
	 */
	@Override
	public Iterator<ClientRegistration> iterator() {
		return this.clientIdToClientRegistration.values().iterator();
	}

	private static Map<String, ClientRegistration> toUnmodifiableConcurrentMap(List<ClientRegistration> registrations) {
		Assert.notEmpty(registrations, "registrations cannot be null or empty");
		ConcurrentHashMap<String, ClientRegistration> result = new ConcurrentHashMap<>();
		for (ClientRegistration registration : registrations) {
			Assert.notNull(registration, "no registration can be null");
			if (result.containsKey(registration.getRegistrationId())) {
				throw new IllegalStateException(String.format("Duplicate key %s", registration.getRegistrationId()));
			}
			result.put(registration.getRegistrationId(), registration);
		}
		return Collections.unmodifiableMap(result);
	}

}
