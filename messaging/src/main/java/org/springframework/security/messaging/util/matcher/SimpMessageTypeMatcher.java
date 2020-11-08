package org.springframework.security.messaging.util.matcher;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * A {@link MessageMatcher} that matches if the provided {@link Message} has a type that
 * is the same as the {@link SimpMessageType} that was specified in the constructor.
 *
 * @author Rob Winch
 * @since 4.0
 *
 */
public class SimpMessageTypeMatcher implements MessageMatcher<Object> {

	private final SimpMessageType typeToMatch;

	/**
	 * Creates a new instance
	 * @param typeToMatch the {@link SimpMessageType} that will result in a match. Cannot
	 * be null.
	 */
	public SimpMessageTypeMatcher(SimpMessageType typeToMatch) {
		Assert.notNull(typeToMatch, "typeToMatch cannot be null");
		this.typeToMatch = typeToMatch;
	}

	@Override
	public boolean matches(Message<?> message) {
		MessageHeaders headers = message.getHeaders();
		SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);
		return this.typeToMatch == messageType;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SimpMessageTypeMatcher)) {
			return false;
		}
		SimpMessageTypeMatcher otherMatcher = (SimpMessageTypeMatcher) other;
		return ObjectUtils.nullSafeEquals(this.typeToMatch, otherMatcher.typeToMatch);
	}

	@Override
	public int hashCode() {
		// Using nullSafeHashCode for proper array hashCode handling
		return ObjectUtils.nullSafeHashCode(this.typeToMatch);
	}

	@Override
	public String toString() {
		return "SimpMessageTypeMatcher [typeToMatch=" + this.typeToMatch + "]";
	}

}
