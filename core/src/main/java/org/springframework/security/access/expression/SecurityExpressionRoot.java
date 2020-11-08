package org.springframework.security.access.expression;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Base root object for use in Spring Security expression evaluations.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public abstract class SecurityExpressionRoot implements SecurityExpressionOperations {

	protected final Authentication authentication;

	private AuthenticationTrustResolver trustResolver;

	private RoleHierarchy roleHierarchy;

	private Set<String> roles;

	private String defaultRolePrefix = "ROLE_";

	/**
	 * Allows "permitAll" expression
	 */
	public final boolean permitAll = true;

	/**
	 * Allows "denyAll" expression
	 */
	public final boolean denyAll = false;

	private PermissionEvaluator permissionEvaluator;

	public final String read = "read";

	public final String write = "write";

	public final String create = "create";

	public final String delete = "delete";

	public final String admin = "administration";

	/**
	 * Creates a new instance
	 * @param authentication the {@link Authentication} to use. Cannot be null.
	 */
	public SecurityExpressionRoot(Authentication authentication) {
		if (authentication == null) {
			throw new IllegalArgumentException("Authentication object cannot be null");
		}
		this.authentication = authentication;
	}

	@Override
	public final boolean hasAuthority(String authority) {
		return hasAnyAuthority(authority);
	}

	@Override
	public final boolean hasAnyAuthority(String... authorities) {
		return hasAnyAuthorityName(null, authorities);
	}

	@Override
	public final boolean hasRole(String role) {
		return hasAnyRole(role);
	}

	@Override
	public final boolean hasAnyRole(String... roles) {
		return hasAnyAuthorityName(this.defaultRolePrefix, roles);
	}

	private boolean hasAnyAuthorityName(String prefix, String... roles) {
		Set<String> roleSet = getAuthoritySet();
		for (String role : roles) {
			String defaultedRole = getRoleWithDefaultPrefix(prefix, role);
			if (roleSet.contains(defaultedRole)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Authentication getAuthentication() {
		return this.authentication;
	}

	@Override
	public final boolean permitAll() {
		return true;
	}

	@Override
	public final boolean denyAll() {
		return false;
	}

	@Override
	public final boolean isAnonymous() {
		return this.trustResolver.isAnonymous(this.authentication);
	}

	@Override
	public final boolean isAuthenticated() {
		return !isAnonymous();
	}

	@Override
	public final boolean isRememberMe() {
		return this.trustResolver.isRememberMe(this.authentication);
	}

	@Override
	public final boolean isFullyAuthenticated() {
		return !this.trustResolver.isAnonymous(this.authentication)
				&& !this.trustResolver.isRememberMe(this.authentication);
	}

	/**
	 * Convenience method to access {@link Authentication#getPrincipal()} from
	 * {@link #getAuthentication()}
	 * @return
	 */
	public Object getPrincipal() {
		return this.authentication.getPrincipal();
	}

	public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
		this.trustResolver = trustResolver;
	}

	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	/**
	 * <p>
	 * Sets the default prefix to be added to {@link #hasAnyRole(String...)} or
	 * {@link #hasRole(String)}. For example, if hasRole("ADMIN") or hasRole("ROLE_ADMIN")
	 * is passed in, then the role ROLE_ADMIN will be used when the defaultRolePrefix is
	 * "ROLE_" (default).
	 * </p>
	 *
	 * <p>
	 * If null or empty, then no default role prefix is used.
	 * </p>
	 * @param defaultRolePrefix the default prefix to add to roles. Default "ROLE_".
	 */
	public void setDefaultRolePrefix(String defaultRolePrefix) {
		this.defaultRolePrefix = defaultRolePrefix;
	}

	private Set<String> getAuthoritySet() {
		if (this.roles == null) {
			Collection<? extends GrantedAuthority> userAuthorities = this.authentication.getAuthorities();
			if (this.roleHierarchy != null) {
				userAuthorities = this.roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
			}
			this.roles = AuthorityUtils.authorityListToSet(userAuthorities);
		}
		return this.roles;
	}

	@Override
	public boolean hasPermission(Object target, Object permission) {
		return this.permissionEvaluator.hasPermission(this.authentication, target, permission);
	}

	@Override
	public boolean hasPermission(Object targetId, String targetType, Object permission) {
		return this.permissionEvaluator.hasPermission(this.authentication, (Serializable) targetId, targetType,
				permission);
	}

	public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
		this.permissionEvaluator = permissionEvaluator;
	}

	/**
	 * Prefixes role with defaultRolePrefix if defaultRolePrefix is non-null and if role
	 * does not already start with defaultRolePrefix.
	 * @param defaultRolePrefix
	 * @param role
	 * @return
	 */
	private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
		if (role == null) {
			return role;
		}
		if (defaultRolePrefix == null || defaultRolePrefix.length() == 0) {
			return role;
		}
		if (role.startsWith(defaultRolePrefix)) {
			return role;
		}
		return defaultRolePrefix + role;
	}

}
