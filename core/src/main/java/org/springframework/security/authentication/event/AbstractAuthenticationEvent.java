package org.springframework.security.authentication.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

/**
 * Represents an application authentication event.
 * <P>
 * The <code>ApplicationEvent</code>'s <code>source</code> will be the
 * <code>Authentication</code> object.
 * </p>
 *
 * @author Ben Alex
 */
public abstract class AbstractAuthenticationEvent extends ApplicationEvent {

	public AbstractAuthenticationEvent(Authentication authentication) {
		super(authentication);
	}

	/**
	 * Getters for the <code>Authentication</code> request that caused the event. Also
	 * available from <code>super.getSource()</code>.
	 * @return the authentication request
	 */
	public Authentication getAuthentication() {
		return (Authentication) super.getSource();
	}

}
