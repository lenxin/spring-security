package org.springframework.security.core.session;

/**
 * Generic "session ID changed" event which indicates that a session identifier
 * (potentially represented by a security context) has changed.
 *
 * @since 5.4
 */
public abstract class SessionIdChangedEvent extends AbstractSessionEvent {

	public SessionIdChangedEvent(Object source) {
		super(source);
	}

	/**
	 * Returns the old session ID.
	 * @return the identifier that was previously associated with the session.
	 */
	public abstract String getOldSessionId();

	/**
	 * Returns the new session ID.
	 * @return the new identifier that is associated with the session.
	 */
	public abstract String getNewSessionId();

}
