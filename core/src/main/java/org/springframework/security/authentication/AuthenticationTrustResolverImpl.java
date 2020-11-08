package org.springframework.security.authentication;

import org.springframework.security.core.Authentication;

/**
 * Basic implementation of {@link AuthenticationTrustResolver}.
 * <p>
 * Makes trust decisions based on whether the passed <code>Authentication</code> is an
 * instance of a defined class.
 * <p>
 * If {@link #anonymousClass} or {@link #rememberMeClass} is <code>null</code>, the
 * corresponding method will always return <code>false</code>.
 *
 * @author Ben Alex
 */
public class AuthenticationTrustResolverImpl implements AuthenticationTrustResolver {

	private Class<? extends Authentication> anonymousClass = AnonymousAuthenticationToken.class;

	private Class<? extends Authentication> rememberMeClass = RememberMeAuthenticationToken.class;

	Class<? extends Authentication> getAnonymousClass() {
		return this.anonymousClass;
	}

	Class<? extends Authentication> getRememberMeClass() {
		return this.rememberMeClass;
	}

	@Override
	public boolean isAnonymous(Authentication authentication) {
		if ((this.anonymousClass == null) || (authentication == null)) {
			return false;
		}
		return this.anonymousClass.isAssignableFrom(authentication.getClass());
	}

	@Override
	public boolean isRememberMe(Authentication authentication) {
		if ((this.rememberMeClass == null) || (authentication == null)) {
			return false;
		}
		return this.rememberMeClass.isAssignableFrom(authentication.getClass());
	}

	public void setAnonymousClass(Class<? extends Authentication> anonymousClass) {
		this.anonymousClass = anonymousClass;
	}

	public void setRememberMeClass(Class<? extends Authentication> rememberMeClass) {
		this.rememberMeClass = rememberMeClass;
	}

}
