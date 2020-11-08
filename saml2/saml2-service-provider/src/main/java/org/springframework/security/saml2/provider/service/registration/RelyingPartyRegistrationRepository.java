package org.springframework.security.saml2.provider.service.registration;

/**
 * A repository for {@link RelyingPartyRegistration}s
 *
 * @author Filip Hanik
 * @since 5.2
 */
public interface RelyingPartyRegistrationRepository {

	/**
	 * Returns the relying party registration identified by the provided
	 * {@code registrationId}, or {@code null} if not found.
	 * @param registrationId the registration identifier
	 * @return the {@link RelyingPartyRegistration} if found, otherwise {@code null}
	 */
	RelyingPartyRegistration findByRegistrationId(String registrationId);

}
