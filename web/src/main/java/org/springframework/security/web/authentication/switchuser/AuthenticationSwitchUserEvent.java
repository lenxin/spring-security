package org.springframework.security.web.authentication.switchuser;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Application event which indicates that a user context switch.
 *
 * @author Mark St.Godard
 */
public class AuthenticationSwitchUserEvent extends AbstractAuthenticationEvent {

	private final UserDetails targetUser;

	/**
	 * Switch user context event constructor
	 * @param authentication The current <code>Authentication</code> object
	 * @param targetUser The target user
	 */
	public AuthenticationSwitchUserEvent(Authentication authentication, UserDetails targetUser) {
		super(authentication);
		this.targetUser = targetUser;
	}

	public UserDetails getTargetUser() {
		return this.targetUser;
	}

}
