package org.springframework.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;

/**
 * ApplicationListener which collects events for use in test assertions
 *
 * @author Luke Taylor
 * @since 3.1
 */
public class CollectingAppListener implements ApplicationListener {

	Set<ApplicationEvent> events = new HashSet<>();

	Set<AbstractAuthenticationEvent> authenticationEvents = new HashSet<>();

	Set<AbstractAuthenticationFailureEvent> authenticationFailureEvents = new HashSet<>();

	Set<AbstractAuthorizationEvent> authorizationEvents = new HashSet<>();

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AbstractAuthenticationEvent) {
			this.events.add(event);
			this.authenticationEvents.add((AbstractAuthenticationEvent) event);
		}
		if (event instanceof AbstractAuthenticationFailureEvent) {
			this.events.add(event);
			this.authenticationFailureEvents.add((AbstractAuthenticationFailureEvent) event);
		}
		if (event instanceof AbstractAuthorizationEvent) {
			this.events.add(event);
			this.authorizationEvents.add((AbstractAuthorizationEvent) event);
		}
	}

	public Set<ApplicationEvent> getEvents() {
		return this.events;
	}

	public Set<AbstractAuthenticationEvent> getAuthenticationEvents() {
		return this.authenticationEvents;
	}

	public Set<AbstractAuthenticationFailureEvent> getAuthenticationFailureEvents() {
		return this.authenticationFailureEvents;
	}

	public Set<AbstractAuthorizationEvent> getAuthorizationEvents() {
		return this.authorizationEvents;
	}

}
