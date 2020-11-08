package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Represents a <code>GrantedAuthority</code> as a <code>Sid</code>.
 * <p>
 * This is a basic implementation that simply uses the <code>String</code>-based principal
 * for <code>Sid</code> comparison. More complex principal objects may wish to provide an
 * alternative <code>Sid</code> implementation that uses some other identifier.
 * </p>
 *
 * @author Ben Alex
 */
public class GrantedAuthoritySid implements Sid {

	private final String grantedAuthority;

	public GrantedAuthoritySid(String grantedAuthority) {
		Assert.hasText(grantedAuthority, "GrantedAuthority required");
		this.grantedAuthority = grantedAuthority;
	}

	public GrantedAuthoritySid(GrantedAuthority grantedAuthority) {
		Assert.notNull(grantedAuthority, "GrantedAuthority required");
		Assert.notNull(grantedAuthority.getAuthority(),
				"This Sid is only compatible with GrantedAuthoritys that provide a non-null getAuthority()");
		this.grantedAuthority = grantedAuthority.getAuthority();
	}

	@Override
	public boolean equals(Object object) {
		if ((object == null) || !(object instanceof GrantedAuthoritySid)) {
			return false;
		}
		// Delegate to getGrantedAuthority() to perform actual comparison (both should be
		// identical)
		return ((GrantedAuthoritySid) object).getGrantedAuthority().equals(this.getGrantedAuthority());
	}

	@Override
	public int hashCode() {
		return this.getGrantedAuthority().hashCode();
	}

	public String getGrantedAuthority() {
		return this.grantedAuthority;
	}

	@Override
	public String toString() {
		return "GrantedAuthoritySid[" + this.grantedAuthority + "]";
	}

}
