package org.springframework.security.messaging.util.matcher;

import java.util.List;

import org.springframework.core.log.LogMessage;
import org.springframework.messaging.Message;

/**
 * {@link MessageMatcher} that will return true if all of the passed in
 * {@link MessageMatcher} instances match.
 *
 * @since 4.0
 */
public final class AndMessageMatcher<T> extends AbstractMessageMatcherComposite<T> {

	/**
	 * Creates a new instance
	 * @param messageMatchers the {@link MessageMatcher} instances to try
	 */
	public AndMessageMatcher(List<MessageMatcher<T>> messageMatchers) {
		super(messageMatchers);
	}

	/**
	 * Creates a new instance
	 * @param messageMatchers the {@link MessageMatcher} instances to try
	 */
	@SafeVarargs
	public AndMessageMatcher(MessageMatcher<T>... messageMatchers) {
		super(messageMatchers);

	}

	@Override
	public boolean matches(Message<? extends T> message) {
		for (MessageMatcher<T> matcher : getMessageMatchers()) {
			this.logger.debug(LogMessage.format("Trying to match using %s", matcher));
			if (!matcher.matches(message)) {
				this.logger.debug("Did not match");
				return false;
			}
		}
		this.logger.debug("All messageMatchers returned true");
		return true;
	}

}
