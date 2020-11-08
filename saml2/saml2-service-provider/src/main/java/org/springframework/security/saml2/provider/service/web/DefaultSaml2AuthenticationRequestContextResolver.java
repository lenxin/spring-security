package org.springframework.security.saml2.provider.service.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticationRequestContext;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.util.Assert;

/**
 * The default implementation for {@link Saml2AuthenticationRequestContextResolver} which
 * uses the current request and given relying party to formulate a
 * {@link Saml2AuthenticationRequestContext}
 *
 * @author Shazin Sadakath
 * @author Josh Cummings
 * @since 5.4
 */
public final class DefaultSaml2AuthenticationRequestContextResolver
		implements Saml2AuthenticationRequestContextResolver {

	private final Log logger = LogFactory.getLog(getClass());

	private final Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver;

	public DefaultSaml2AuthenticationRequestContextResolver(
			Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver) {
		this.relyingPartyRegistrationResolver = relyingPartyRegistrationResolver;
	}

	@Override
	public Saml2AuthenticationRequestContext resolve(HttpServletRequest request) {
		Assert.notNull(request, "request cannot be null");
		RelyingPartyRegistration relyingParty = this.relyingPartyRegistrationResolver.convert(request);
		if (relyingParty == null) {
			return null;
		}
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Creating SAML 2.0 Authentication Request for Asserting Party ["
					+ relyingParty.getRegistrationId() + "]");
		}
		return createRedirectAuthenticationRequestContext(request, relyingParty);
	}

	private Saml2AuthenticationRequestContext createRedirectAuthenticationRequestContext(HttpServletRequest request,
			RelyingPartyRegistration relyingParty) {

		return Saml2AuthenticationRequestContext.builder().issuer(relyingParty.getEntityId())
				.relyingPartyRegistration(relyingParty)
				.assertionConsumerServiceUrl(relyingParty.getAssertionConsumerServiceLocation())
				.relayState(request.getParameter("RelayState")).build();
	}

}
