package org.springframework.security.authentication.jaas;

import javax.security.auth.login.LoginException;

import org.springframework.security.core.AuthenticationException;

/**
 * The JaasAuthenticationProvider takes an instance of LoginExceptionResolver to resolve
 * LoginModule specific exceptions to Spring Security <tt>AuthenticationException</tt>s.
 * For instance, a configured login module could throw a ScrewedUpPasswordException that
 * extends LoginException, in this instance the LoginExceptionResolver implementation
 * would return a
 * {@link org.springframework.security.authentication.BadCredentialsException}.
 *
 * @author Ray Krueger
 */
public interface LoginExceptionResolver {

	/**
	 * Translates a Jaas LoginException to an SpringSecurityException.
	 * @param ex The LoginException thrown by the configured LoginModule.
	 * @return The AuthenticationException that the JaasAuthenticationProvider should
	 * throw.
	 */
	AuthenticationException resolveException(LoginException ex);

}
