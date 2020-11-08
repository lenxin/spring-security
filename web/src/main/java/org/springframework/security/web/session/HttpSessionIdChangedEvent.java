package org.springframework.security.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.session.SessionIdChangedEvent;

/**
 * Published by the {@link HttpSessionEventPublisher} when an {@link HttpSession} ID is
 * changed.
 *
 * @since 5.4
 */
public class HttpSessionIdChangedEvent extends SessionIdChangedEvent {

	private final String oldSessionId;

	private final String newSessionId;

	public HttpSessionIdChangedEvent(HttpSession session, String oldSessionId) {
		super(session);
		this.oldSessionId = oldSessionId;
		this.newSessionId = session.getId();
	}

	@Override
	public String getOldSessionId() {
		return this.oldSessionId;
	}

	@Override
	public String getNewSessionId() {
		return this.newSessionId;
	}

}
