package org.springframework.security.web.session;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Listener for tests
 *
 * @author Ray Krueger
 */
public class MockApplicationListener implements ApplicationListener<ApplicationEvent> {

	private HttpSessionCreatedEvent createdEvent;

	private HttpSessionDestroyedEvent destroyedEvent;

	private HttpSessionIdChangedEvent sessionIdChangedEvent;

	public HttpSessionCreatedEvent getCreatedEvent() {
		return this.createdEvent;
	}

	public HttpSessionDestroyedEvent getDestroyedEvent() {
		return this.destroyedEvent;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof HttpSessionCreatedEvent) {
			this.createdEvent = (HttpSessionCreatedEvent) event;
		}
		else if (event instanceof HttpSessionDestroyedEvent) {
			this.destroyedEvent = (HttpSessionDestroyedEvent) event;
		}
		else if (event instanceof HttpSessionIdChangedEvent) {
			this.sessionIdChangedEvent = (HttpSessionIdChangedEvent) event;
		}
	}

	public void setCreatedEvent(HttpSessionCreatedEvent createdEvent) {
		this.createdEvent = createdEvent;
	}

	public void setDestroyedEvent(HttpSessionDestroyedEvent destroyedEvent) {
		this.destroyedEvent = destroyedEvent;
	}

	public void setSessionIdChangedEvent(HttpSessionIdChangedEvent sessionIdChangedEvent) {
		this.sessionIdChangedEvent = sessionIdChangedEvent;
	}

	public HttpSessionIdChangedEvent getSessionIdChangedEvent() {
		return this.sessionIdChangedEvent;
	}

}
