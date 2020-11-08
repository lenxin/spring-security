package org.springframework.security.core.session;

/**
 * Generic session creation event which indicates that a session (potentially represented
 * by a security context) has begun.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public abstract class SessionCreationEvent extends AbstractSessionEvent {

	public SessionCreationEvent(Object source) {
		super(source);
	}

}
