package org.springframework.security.access.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationListener;
import org.springframework.core.log.LogMessage;

/**
 * Outputs interceptor-related application events to Commons Logging.
 * <p>
 * All failures are logged at the warning level, with success events logged at the
 * information level, and public invocation events logged at the debug level.
 * </p>
 *
 * @author Ben Alex
 */
public class LoggerListener implements ApplicationListener<AbstractAuthorizationEvent> {

	private static final Log logger = LogFactory.getLog(LoggerListener.class);

	@Override
	public void onApplicationEvent(AbstractAuthorizationEvent event) {
		if (event instanceof AuthenticationCredentialsNotFoundEvent) {
			onAuthenticationCredentialsNotFoundEvent((AuthenticationCredentialsNotFoundEvent) event);
		}
		if (event instanceof AuthorizationFailureEvent) {
			onAuthorizationFailureEvent((AuthorizationFailureEvent) event);
		}
		if (event instanceof AuthorizedEvent) {
			onAuthorizedEvent((AuthorizedEvent) event);
		}
		if (event instanceof PublicInvocationEvent) {
			onPublicInvocationEvent((PublicInvocationEvent) event);
		}
	}

	private void onAuthenticationCredentialsNotFoundEvent(AuthenticationCredentialsNotFoundEvent authEvent) {
		logger.warn(LogMessage.format(
				"Security interception failed due to: %s; secure object: %s; configuration attributes: %s",
				authEvent.getCredentialsNotFoundException(), authEvent.getSource(), authEvent.getConfigAttributes()));
	}

	private void onPublicInvocationEvent(PublicInvocationEvent event) {
		logger.info(LogMessage.format("Security interception not required for public secure object: %s",
				event.getSource()));
	}

	private void onAuthorizedEvent(AuthorizedEvent authEvent) {
		logger.info(LogMessage.format(
				"Security authorized for authenticated principal: %s; secure object: %s; configuration attributes: %s",
				authEvent.getAuthentication(), authEvent.getSource(), authEvent.getConfigAttributes()));
	}

	private void onAuthorizationFailureEvent(AuthorizationFailureEvent authEvent) {
		logger.warn(LogMessage.format(
				"Security authorization failed due to: %s; authenticated principal: %s; secure object: %s; configuration attributes: %s",
				authEvent.getAccessDeniedException(), authEvent.getAuthentication(), authEvent.getSource(),
				authEvent.getConfigAttributes()));
	}

}
