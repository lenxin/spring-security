package org.springframework.security.messaging.util.matcher;

import org.junit.Before;
import org.junit.Test;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SimpMessageTypeMatcherTests {

	private SimpMessageTypeMatcher matcher;

	@Before
	public void setup() {
		this.matcher = new SimpMessageTypeMatcher(SimpMessageType.MESSAGE);
	}

	@Test
	public void constructorNullType() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SimpMessageTypeMatcher(null));
	}

	@Test
	public void matchesMessageMessageTrue() {
		// @formatter:off
		Message<String> message = MessageBuilder.withPayload("Hi")
				.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.MESSAGE)
				.build();
		// @formatter:on
		assertThat(this.matcher.matches(message)).isTrue();
	}

	@Test
	public void matchesMessageConnectFalse() {
		// @formatter:off
		Message<String> message = MessageBuilder.withPayload("Hi")
				.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.CONNECT)
				.build();
		// @formatter:on
		assertThat(this.matcher.matches(message)).isFalse();
	}

	@Test
	public void matchesMessageNullFalse() {
		Message<String> message = MessageBuilder.withPayload("Hi").build();
		assertThat(this.matcher.matches(message)).isFalse();
	}

}
