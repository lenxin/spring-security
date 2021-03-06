package org.springframework.security.saml2.provider.service.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * An implementation of an {@link AbstractAuthenticationToken} that represents an
 * authenticated SAML 2.0 {@link Authentication}.
 * <p>
 * The {@link Authentication} associates valid SAML assertion data with a Spring Security
 * authentication object The complete assertion is contained in the object in String
 * format, {@link Saml2Authentication#getSaml2Response()}
 *
 * @since 5.2
 * @see AbstractAuthenticationToken
 */
public class Saml2Authentication extends AbstractAuthenticationToken {

	private final AuthenticatedPrincipal principal;

	private final String saml2Response;

	public Saml2Authentication(AuthenticatedPrincipal principal, String saml2Response,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		Assert.notNull(principal, "principal cannot be null");
		Assert.hasText(saml2Response, "saml2Response cannot be null");
		this.principal = principal;
		this.saml2Response = saml2Response;
		setAuthenticated(true);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	/**
	 * Returns the SAML response object, as decoded XML. May contain encrypted elements
	 * @return string representation of the SAML Response XML object
	 */
	public String getSaml2Response() {
		return this.saml2Response;
	}

	@Override
	public Object getCredentials() {
		return getSaml2Response();
	}

}
