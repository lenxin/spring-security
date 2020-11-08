package org.springframework.security.access.event;

import org.springframework.context.ApplicationEvent;

/**
 * Abstract superclass for all security interception related events.
 *
 * @author Ben Alex
 */
public abstract class AbstractAuthorizationEvent extends ApplicationEvent {

	/**
	 * Construct the event, passing in the secure object being intercepted.
	 * @param secureObject the secure object
	 */
	public AbstractAuthorizationEvent(Object secureObject) {
		super(secureObject);
	}

}
