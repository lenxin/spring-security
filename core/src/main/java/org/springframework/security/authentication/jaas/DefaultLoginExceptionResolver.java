package org.springframework.security.authentication.jaas;

import javax.security.auth.login.LoginException;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

/**
 * This LoginExceptionResolver simply wraps the LoginException with an
 * AuthenticationServiceException.
 *
 * @author Ray Krueger
 */
public class DefaultLoginExceptionResolver implements LoginExceptionResolver {

	@Override
	public AuthenticationException resolveException(LoginException ex) {
		return new AuthenticationServiceException(ex.getMessage(), ex);
	}

}
