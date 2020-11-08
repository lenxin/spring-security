package org.springframework.security.authentication.event;

import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Indicates an interactive authentication was successful.
 * <P>
 * The <code>ApplicationEvent</code>'s <code>source</code> will be the
 * <code>Authentication</code> object.
 * </p>
 * <p>
 * This does not extend from <code>AuthenticationSuccessEvent</code> to avoid duplicate
 * <code>AuthenticationSuccessEvent</code>s being sent to any listeners.
 * </p>
 *
 * @author Ben Alex
 */
public class InteractiveAuthenticationSuccessEvent extends AbstractAuthenticationEvent {

	private final Class<?> generatedBy;

	public InteractiveAuthenticationSuccessEvent(Authentication authentication, Class<?> generatedBy) {
		super(authentication);
		Assert.notNull(generatedBy, "generatedBy cannot be null");
		this.generatedBy = generatedBy;
	}

	/**
	 * Getter for the <code>Class</code> that generated this event. This can be useful for
	 * generating additional logging information.
	 * @return the class
	 */
	public Class<?> getGeneratedBy() {
		return this.generatedBy;
	}

}
