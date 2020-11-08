package org.springframework.security.access.intercept;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

/**
 * Implementation of a {@link RunAsManager} that does nothing.
 * <p>
 * This class should be used if you do not require run-as authentication replacement
 * functionality.
 *
 * @author Ben Alex
 */
final class NullRunAsManager implements RunAsManager {

	@Override
	public Authentication buildRunAs(Authentication authentication, Object object, Collection<ConfigAttribute> config) {
		return null;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
