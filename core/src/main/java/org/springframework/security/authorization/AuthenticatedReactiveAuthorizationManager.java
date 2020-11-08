package org.springframework.security.authorization;

import reactor.core.publisher.Mono;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * A {@link ReactiveAuthorizationManager} that determines if the current user is
 * authenticated.
 *
 * @param <T> The type of object authorization is being performed against. This does not
 * @author Rob Winch
 * @since 5.0 matter since the authorization decision does not use the object.
 */
public class AuthenticatedReactiveAuthorizationManager<T> implements ReactiveAuthorizationManager<T> {

	private AuthenticationTrustResolver authTrustResolver = new AuthenticationTrustResolverImpl();

	AuthenticatedReactiveAuthorizationManager() {
	}

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T object) {
		return authentication.filter(this::isNotAnonymous).map(this::getAuthorizationDecision)
				.defaultIfEmpty(new AuthorizationDecision(false));
	}

	private AuthorizationDecision getAuthorizationDecision(Authentication authentication) {
		return new AuthorizationDecision(authentication.isAuthenticated());
	}

	/**
	 * Verify (via {@link AuthenticationTrustResolver}) that the given authentication is
	 * not anonymous.
	 * @param authentication to be checked
	 * @return <code>true</code> if not anonymous, otherwise <code>false</code>.
	 */
	private boolean isNotAnonymous(Authentication authentication) {
		return !this.authTrustResolver.isAnonymous(authentication);
	}

	/**
	 * Gets an instance of {@link AuthenticatedReactiveAuthorizationManager}
	 * @param <T>
	 * @return
	 */
	public static <T> AuthenticatedReactiveAuthorizationManager<T> authenticated() {
		return new AuthenticatedReactiveAuthorizationManager<>();
	}

}
