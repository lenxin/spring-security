package org.springframework.security.config.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * An {@link AuthenticationProvider} that has an {@link Autowired} constructor which is
 * necessary to recreate SEC-1885.
 *
 * @author Rob Winch
 *
 */
@Service("authProvider")
public class TestAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	public TestAuthenticationProvider(AuthProviderDependency authProviderDependency) {
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		throw new UnsupportedOperationException();
	}

}
