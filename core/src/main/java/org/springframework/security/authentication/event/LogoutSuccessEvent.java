package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;

/**
 * Application event which indicates successful logout
 *
 * @author Onur Kagan Ozcan
 * @since 5.2.0
 */
public class LogoutSuccessEvent extends AbstractAuthenticationEvent {

	public LogoutSuccessEvent(Authentication authentication) {
		super(authentication);
	}

}
