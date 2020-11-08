package org.springframework.security.oauth2.client.registration;

import reactor.core.publisher.Mono;

/**
 * A reactive repository for OAuth 2.0 / OpenID Connect 1.0 {@link ClientRegistration}(s).
 *
 * <p>
 * <b>NOTE:</b> Client registration information is ultimately stored and owned by the
 * associated Authorization Server. Therefore, this repository provides the capability to
 * store a sub-set copy of the <i>primary</i> client registration information externally
 * from the Authorization Server.
 *
 * @author Rob Winch
 * @since 5.1
 * @see ClientRegistration
 */
public interface ReactiveClientRegistrationRepository {

	/**
	 * Returns the client registration identified by the provided {@code registrationId},
	 * or {@code null} if not found.
	 * @param registrationId the registration identifier
	 * @return the {@link ClientRegistration} if found, otherwise {@code null}
	 */
	Mono<ClientRegistration> findByRegistrationId(String registrationId);

}
