package org.springframework.security.config.annotation.method.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Rob Winch
 * @since 5.0
 */
@Component
public class Authz {

	public boolean check(boolean result) {
		return result;
	}

	public boolean check(long id) {
		return id % 2 == 0;
	}

	public boolean check(Authentication authentication, String message) {
		return message != null && message.contains(authentication.getName());
	}

}
