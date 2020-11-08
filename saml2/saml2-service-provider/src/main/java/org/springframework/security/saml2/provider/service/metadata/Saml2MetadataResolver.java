package org.springframework.security.saml2.provider.service.metadata;

import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;

/**
 * Resolves the SAML 2.0 Relying Party Metadata for a given
 * {@link RelyingPartyRegistration}
 *
 * @author Jakub Kubrynski
 * @author Josh Cummings
 * @since 5.4
 */
public interface Saml2MetadataResolver {

	/**
	 * Resolve the given relying party's metadata
	 * @param relyingPartyRegistration the relying party
	 * @return the relying party's metadata
	 */
	String resolve(RelyingPartyRegistration relyingPartyRegistration);

}
