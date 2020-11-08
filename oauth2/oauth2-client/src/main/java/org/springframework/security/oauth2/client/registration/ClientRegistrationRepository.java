package org.springframework.security.oauth2.client.registration;

/**
 * A repository for OAuth 2.0 / OpenID Connect 1.0 {@link ClientRegistration}(s).
 *
 * <p>
 * <b>NOTE:</b> Client registration information is ultimately stored and owned by the
 * associated Authorization Server. Therefore, this repository provides the capability to
 * store a sub-set copy of the <i>primary</i> client registration information externally
 * from the Authorization Server.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see ClientRegistration
 */
public interface ClientRegistrationRepository {

	/**
	 * Returns the client registration identified by the provided {@code registrationId},
	 * or {@code null} if not found.
	 * @param registrationId the registration identifier
	 * @return the {@link ClientRegistration} if found, otherwise {@code null}
	 */
	ClientRegistration findByRegistrationId(String registrationId);

}
