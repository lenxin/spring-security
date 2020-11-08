package org.springframework.security.core.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.ObjectUtils;

/**
 * Base implementation of {@link SecurityContext}.
 * <p>
 * Used by default by {@link SecurityContextHolder} strategies.
 *
 * @author Ben Alex
 */
public class SecurityContextImpl implements SecurityContext {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private Authentication authentication;

	public SecurityContextImpl() {
	}

	public SecurityContextImpl(Authentication authentication) {
		this.authentication = authentication;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SecurityContextImpl) {
			SecurityContextImpl other = (SecurityContextImpl) obj;
			if ((this.getAuthentication() == null) && (other.getAuthentication() == null)) {
				return true;
			}
			if ((this.getAuthentication() != null) && (other.getAuthentication() != null)
					&& this.getAuthentication().equals(other.getAuthentication())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Authentication getAuthentication() {
		return this.authentication;
	}

	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.authentication);
	}

	@Override
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append(" [");
		if (this.authentication == null) {
			sb.append("Null authentication");
		}
		else {
			sb.append("Authentication=").append(this.authentication);
		}
		sb.append("]");
		return sb.toString();
	}

}
