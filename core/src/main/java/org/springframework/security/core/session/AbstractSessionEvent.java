package org.springframework.security.core.session;

import org.springframework.context.ApplicationEvent;

/**
 * Abstract superclass for all session related events.
 *
 * @author Eleftheria Stein
 * @since 5.4
 */
public class AbstractSessionEvent extends ApplicationEvent {

	public AbstractSessionEvent(Object source) {
		super(source);
	}

}
