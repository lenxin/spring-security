package org.springframework.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Rob Winch
 * @since 5.0.2
 */
public class MockEventListener<T extends ApplicationEvent> implements ApplicationListener<T> {

	private List<T> events = new ArrayList<>();

	@Override
	public void onApplicationEvent(T event) {
		this.events.add(event);
	}

	public List<T> getEvents() {
		return this.events;
	}

}
