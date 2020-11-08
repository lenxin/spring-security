package org.springframework.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * An {@link AuthenticationProvider} implementation for the
 * {@link TestingAuthenticationToken}.
 * <p>
 * It simply accepts as valid whatever is contained within the
 * <code>TestingAuthenticationToken</code>.
 * </p>
 * <p>
 * The purpose of this implementation is to facilitate unit testing. This provider should
 * <b>never be enabled on a production system</b>.
 *
 * @author Ben Alex
 */
public class TestingAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TestingAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
