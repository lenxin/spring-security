package org.springframework.security.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.session.SessionCreationEvent;

/**
 * Published by the {@link HttpSessionEventPublisher} when an {@code HttpSession} is
 * created by the container
 *
 * @author Ray Krueger
 * @author Luke Taylor
 */
public class HttpSessionCreatedEvent extends SessionCreationEvent {

	public HttpSessionCreatedEvent(HttpSession session) {
		super(session);
	}

	public HttpSession getSession() {
		return (HttpSession) getSource();
	}

}
