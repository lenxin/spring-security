package org.springframework.security.web.authentication.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * A {@link SessionAuthenticationStrategy} that accepts multiple
 * {@link SessionAuthenticationStrategy} implementations to delegate to. Each
 * {@link SessionAuthenticationStrategy} is invoked in turn. The invocations are short
 * circuited if any exception, (i.e. SessionAuthenticationException) is thrown.
 *
 * <p>
 * Typical usage would include having the following delegates (in this order)
 * </p>
 *
 * <ul>
 * <li>{@link ConcurrentSessionControlAuthenticationStrategy} - verifies that a user is
 * allowed to authenticate (i.e. they have not already logged into the application.</li>
 * <li>{@link SessionFixationProtectionStrategy} - If session fixation is desired,
 * {@link SessionFixationProtectionStrategy} should be after
 * {@link ConcurrentSessionControlAuthenticationStrategy} to prevent unnecessary
 * {@link HttpSession} creation if the
 * {@link ConcurrentSessionControlAuthenticationStrategy} rejects authentication.</li>
 * <li>{@link RegisterSessionAuthenticationStrategy} - It is important this is after
 * {@link SessionFixationProtectionStrategy} so that the correct session is registered.
 * </li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public class CompositeSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

	private final Log logger = LogFactory.getLog(getClass());

	private final List<SessionAuthenticationStrategy> delegateStrategies;

	public CompositeSessionAuthenticationStrategy(List<SessionAuthenticationStrategy> delegateStrategies) {
		Assert.notEmpty(delegateStrategies, "delegateStrategies cannot be null or empty");
		for (SessionAuthenticationStrategy strategy : delegateStrategies) {
			Assert.notNull(strategy, () -> "delegateStrategies cannot contain null entires. Got " + delegateStrategies);
		}
		this.delegateStrategies = delegateStrategies;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionAuthenticationException {
		int currentPosition = 0;
		int size = this.delegateStrategies.size();
		for (SessionAuthenticationStrategy delegate : this.delegateStrategies) {
			if (this.logger.isTraceEnabled()) {
				this.logger.trace(LogMessage.format("Preparing session with %s (%d/%d)",
						delegate.getClass().getSimpleName(), ++currentPosition, size));
			}
			delegate.onAuthentication(authentication, request, response);
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + " [delegateStrategies = " + this.delegateStrategies + "]";
	}

}
