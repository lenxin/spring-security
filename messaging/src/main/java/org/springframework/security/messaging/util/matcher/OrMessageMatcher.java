package org.springframework.security.messaging.util.matcher;

import java.util.List;

import org.springframework.core.log.LogMessage;
import org.springframework.messaging.Message;

/**
 * {@link MessageMatcher} that will return true if any of the passed in
 * {@link MessageMatcher} instances match.
 *
 * @since 4.0
 */
public final class OrMessageMatcher<T> extends AbstractMessageMatcherComposite<T> {

	/**
	 * Creates a new instance
	 * @param messageMatchers the {@link MessageMatcher} instances to try
	 */
	public OrMessageMatcher(List<MessageMatcher<T>> messageMatchers) {
		super(messageMatchers);
	}

	/**
	 * Creates a new instance
	 * @param messageMatchers the {@link MessageMatcher} instances to try
	 */
	@SafeVarargs
	public OrMessageMatcher(MessageMatcher<T>... messageMatchers) {
		super(messageMatchers);

	}

	@Override
	public boolean matches(Message<? extends T> message) {
		for (MessageMatcher<T> matcher : getMessageMatchers()) {
			this.logger.debug(LogMessage.format("Trying to match using %s", matcher));
			if (matcher.matches(message)) {
				this.logger.debug("matched");
				return true;
			}
		}
		this.logger.debug("No matches found");
		return false;
	}

}
