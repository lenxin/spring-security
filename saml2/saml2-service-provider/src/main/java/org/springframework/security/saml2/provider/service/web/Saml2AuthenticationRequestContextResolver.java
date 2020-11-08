package org.springframework.security.saml2.provider.service.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticationRequestContext;

/**
 * This {@code Saml2AuthenticationRequestContextResolver} formulates a
 * <a href="https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf">SAML 2.0
 * AuthnRequest</a> (line 1968)
 *
 * @author Shazin Sadakath
 * @author Josh Cummings
 * @since 5.4
 */
public interface Saml2AuthenticationRequestContextResolver {

	/**
	 * This {@code resolve} method is defined to create a
	 * {@link Saml2AuthenticationRequestContext}
	 * @param request the current request
	 * @return the created {@link Saml2AuthenticationRequestContext} for the request
	 */
	Saml2AuthenticationRequestContext resolve(HttpServletRequest request);

}
