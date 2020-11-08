package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Represents an <code>Authentication.getPrincipal()</code> as a <code>Sid</code>.
 * <p>
 * This is a basic implementation that simply uses the <code>String</code>-based principal
 * for <code>Sid</code> comparison. More complex principal objects may wish to provide an
 * alternative <code>Sid</code> implementation that uses some other identifier.
 * </p>
 *
 * @author Ben Alex
 */
public class PrincipalSid implements Sid {

	private final String principal;

	public PrincipalSid(String principal) {
		Assert.hasText(principal, "Principal required");
		this.principal = principal;
	}

	public PrincipalSid(Authentication authentication) {
		Assert.notNull(authentication, "Authentication required");
		Assert.notNull(authentication.getPrincipal(), "Principal required");
		this.principal = authentication.getName();
	}

	@Override
	public boolean equals(Object object) {
		if ((object == null) || !(object instanceof PrincipalSid)) {
			return false;
		}
		// Delegate to getPrincipal() to perform actual comparison (both should be
		// identical)
		return ((PrincipalSid) object).getPrincipal().equals(this.getPrincipal());
	}

	@Override
	public int hashCode() {
		return this.getPrincipal().hashCode();
	}

	public String getPrincipal() {
		return this.principal;
	}

	@Override
	public String toString() {
		return "PrincipalSid[" + this.principal + "]";
	}

}
