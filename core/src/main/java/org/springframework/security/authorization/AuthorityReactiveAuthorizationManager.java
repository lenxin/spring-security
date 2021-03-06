package org.springframework.security.authorization;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * A {@link ReactiveAuthorizationManager} that determines if the current user is
 * authorized by evaluating if the {@link Authentication} contains a specified authority.
 *
 * @param <T> the type of object being authorized
 * @author Rob Winch
 * @since 5.0
 */
public class AuthorityReactiveAuthorizationManager<T> implements ReactiveAuthorizationManager<T> {

	private final List<String> authorities;

	AuthorityReactiveAuthorizationManager(String... authorities) {
		this.authorities = Arrays.asList(authorities);
	}

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T object) {
		// @formatter:off
		return authentication.filter((a) -> a.isAuthenticated())
				.flatMapIterable(Authentication::getAuthorities)
				.map(GrantedAuthority::getAuthority)
				.any(this.authorities::contains)
				.map(AuthorizationDecision::new)
				.defaultIfEmpty(new AuthorizationDecision(false));
		// @formatter:on
	}

	/**
	 * Creates an instance of {@link AuthorityReactiveAuthorizationManager} with the
	 * provided authority.
	 * @param authority the authority to check for
	 * @param <T> the type of object being authorized
	 * @return the new instance
	 */
	public static <T> AuthorityReactiveAuthorizationManager<T> hasAuthority(String authority) {
		Assert.notNull(authority, "authority cannot be null");
		return new AuthorityReactiveAuthorizationManager<>(authority);
	}

	/**
	 * Creates an instance of {@link AuthorityReactiveAuthorizationManager} with the
	 * provided authorities.
	 *
	 * @author Robbie Martinus
	 * @param authorities the authorities to check for
	 * @param <T> the type of object being authorized
	 * @return the new instance
	 */
	public static <T> AuthorityReactiveAuthorizationManager<T> hasAnyAuthority(String... authorities) {
		Assert.notNull(authorities, "authorities cannot be null");
		for (String authority : authorities) {
			Assert.notNull(authority, "authority cannot be null");
		}
		return new AuthorityReactiveAuthorizationManager<>(authorities);
	}

	/**
	 * Creates an instance of {@link AuthorityReactiveAuthorizationManager} with the
	 * provided authority.
	 * @param role the authority to check for prefixed with "ROLE_"
	 * @param <T> the type of object being authorized
	 * @return the new instance
	 */
	public static <T> AuthorityReactiveAuthorizationManager<T> hasRole(String role) {
		Assert.notNull(role, "role cannot be null");
		return hasAuthority("ROLE_" + role);
	}

	/**
	 * Creates an instance of {@link AuthorityReactiveAuthorizationManager} with the
	 * provided authorities.
	 *
	 * @author Robbie Martinus
	 * @param roles the authorities to check for prefixed with "ROLE_"
	 * @param <T> the type of object being authorized
	 * @return the new instance
	 */
	public static <T> AuthorityReactiveAuthorizationManager<T> hasAnyRole(String... roles) {
		Assert.notNull(roles, "roles cannot be null");
		for (String role : roles) {
			Assert.notNull(role, "role cannot be null");
		}
		return hasAnyAuthority(toNamedRolesArray(roles));
	}

	private static String[] toNamedRolesArray(String... roles) {
		String[] result = new String[roles.length];
		for (int i = 0; i < roles.length; i++) {
			result[i] = "ROLE_" + roles[i];
		}
		return result;
	}

}
