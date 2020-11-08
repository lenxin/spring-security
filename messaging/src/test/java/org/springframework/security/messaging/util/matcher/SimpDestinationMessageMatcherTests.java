package org.springframework.security.messaging.util.matcher;

import org.junit.Before;
import org.junit.Test;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SimpDestinationMessageMatcherTests {

	MessageBuilder<String> messageBuilder;

	SimpDestinationMessageMatcher matcher;

	PathMatcher pathMatcher;

	@Before
	public void setup() {
		this.messageBuilder = MessageBuilder.withPayload("M");
		this.matcher = new SimpDestinationMessageMatcher("/**");
		this.pathMatcher = new AntPathMatcher();
	}

	@Test
	public void constructorPatternNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SimpDestinationMessageMatcher(null));
	}

	public void constructorOnlyPathNoError() {
		new SimpDestinationMessageMatcher("/path");
	}

	@Test
	public void matchesDoesNotMatchNullDestination() {
		assertThat(this.matcher.matches(this.messageBuilder.build())).isFalse();
	}

	@Test
	public void matchesAllWithDestination() {
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/destination/1");
		assertThat(this.matcher.matches(this.messageBuilder.build())).isTrue();
	}

	@Test
	public void matchesSpecificWithDestination() {
		this.matcher = new SimpDestinationMessageMatcher("/destination/1");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/destination/1");
		assertThat(this.matcher.matches(this.messageBuilder.build())).isTrue();
	}

	@Test
	public void matchesFalseWithDestination() {
		this.matcher = new SimpDestinationMessageMatcher("/nomatch");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/destination/1");
		assertThat(this.matcher.matches(this.messageBuilder.build())).isFalse();
	}

	@Test
	public void matchesFalseMessageTypeNotDisconnectType() {
		this.matcher = SimpDestinationMessageMatcher.createMessageMatcher("/match", this.pathMatcher);
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.DISCONNECT);
		assertThat(this.matcher.matches(this.messageBuilder.build())).isFalse();
	}

	@Test
	public void matchesTrueMessageType() {
		this.matcher = SimpDestinationMessageMatcher.createMessageMatcher("/match", this.pathMatcher);
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/match");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.MESSAGE);
		assertThat(this.matcher.matches(this.messageBuilder.build())).isTrue();
	}

	@Test
	public void matchesTrueSubscribeType() {
		this.matcher = SimpDestinationMessageMatcher.createSubscribeMatcher("/match", this.pathMatcher);
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/match");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.SUBSCRIBE);
		assertThat(this.matcher.matches(this.messageBuilder.build())).isTrue();
	}

	@Test
	public void matchesNullMessageType() {
		this.matcher = new SimpDestinationMessageMatcher("/match");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/match");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.MESSAGE);
		assertThat(this.matcher.matches(this.messageBuilder.build())).isTrue();
	}

	@Test
	public void extractPathVariablesFromDestination() {
		this.matcher = new SimpDestinationMessageMatcher("/topics/{topic}/**");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/topics/someTopic/sub1");
		this.messageBuilder.setHeader(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, SimpMessageType.MESSAGE);
		assertThat(this.matcher.extractPathVariables(this.messageBuilder.build()).get("topic")).isEqualTo("someTopic");
	}

	@Test
	public void extractedVariablesAreEmptyInNullDestination() {
		this.matcher = new SimpDestinationMessageMatcher("/topics/{topic}/**");
		assertThat(this.matcher.extractPathVariables(this.messageBuilder.build())).isEmpty();
	}

	@Test
	public void typeConstructorParameterIsTransmitted() {
		this.matcher = SimpDestinationMessageMatcher.createMessageMatcher("/match", this.pathMatcher);
		MessageMatcher<Object> expectedTypeMatcher = new SimpMessageTypeMatcher(SimpMessageType.MESSAGE);
		assertThat(this.matcher.getMessageTypeMatcher()).isEqualTo(expectedTypeMatcher);
	}

}
