package org.springframework.security.messaging.util.matcher;

import org.springframework.messaging.Message;

/**
 * API for determining if a {@link Message} should be matched on.
 *
 * @author Rob Winch
 * @since 4.0
 */
public interface MessageMatcher<T> {

	/**
	 * Matches every {@link Message}
	 */
	MessageMatcher<Object> ANY_MESSAGE = new MessageMatcher<Object>() {

		@Override
		public boolean matches(Message<?> message) {
			return true;
		}

		@Override
		public String toString() {
			return "ANY_MESSAGE";
		}

	};

	/**
	 * Returns true if the {@link Message} matches, else false
	 * @param message the {@link Message} to match on
	 * @return true if the {@link Message} matches, else false
	 */
	boolean matches(Message<? extends T> message);

}
