package org.springframework.security.cas.authentication;

import java.util.ArrayList;

import org.jasig.cas.client.validation.Assertion;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * Temporary authentication object needed to load the user details service.
 *
 * @author Scott Battaglia
 * @since 3.0
 */
public final class CasAssertionAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Assertion assertion;

	private final String ticket;

	public CasAssertionAuthenticationToken(final Assertion assertion, final String ticket) {
		super(new ArrayList<>());
		this.assertion = assertion;
		this.ticket = ticket;
	}

	@Override
	public Object getPrincipal() {
		return this.assertion.getPrincipal().getName();
	}

	@Override
	public Object getCredentials() {
		return this.ticket;
	}

	public Assertion getAssertion() {
		return this.assertion;
	}

}
