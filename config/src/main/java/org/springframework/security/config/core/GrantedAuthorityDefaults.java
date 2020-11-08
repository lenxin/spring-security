package org.springframework.security.config.core;

import org.springframework.security.core.GrantedAuthority;

/**
 * Allows providing defaults for {@link GrantedAuthority}
 *
 * @author Eddú Meléndez
 * @since 4.2.0
 */
public final class GrantedAuthorityDefaults {

	private final String rolePrefix;

	public GrantedAuthorityDefaults(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	/**
	 * The default prefix used with role based authorization. Default is "ROLE_".
	 * @return the default role prefix
	 */
	public String getRolePrefix() {
		return this.rolePrefix;
	}

}
