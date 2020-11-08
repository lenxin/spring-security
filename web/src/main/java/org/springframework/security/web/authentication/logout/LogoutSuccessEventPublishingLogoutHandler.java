package org.springframework.security.web.authentication.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;

/**
 * A logout handler which publishes {@link LogoutSuccessEvent}
 *
 * @author Onur Kagan Ozcan
 * @since 5.2.0
 */
public final class LogoutSuccessEventPublishingLogoutHandler implements LogoutHandler, ApplicationEventPublisherAware {

	private ApplicationEventPublisher eventPublisher;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (this.eventPublisher == null) {
			return;
		}
		if (authentication == null) {
			return;
		}
		this.eventPublisher.publishEvent(new LogoutSuccessEvent(authentication));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}

}
