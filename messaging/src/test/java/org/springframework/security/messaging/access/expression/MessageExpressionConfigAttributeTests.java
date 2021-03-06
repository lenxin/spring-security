package org.springframework.security.messaging.access.expression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.messaging.util.matcher.MessageMatcher;
import org.springframework.security.messaging.util.matcher.SimpDestinationMessageMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageExpressionConfigAttributeTests {

	@Mock
	Expression expression;

	@Mock
	MessageMatcher<?> matcher;

	MessageExpressionConfigAttribute attribute;

	@Before
	public void setup() {
		this.attribute = new MessageExpressionConfigAttribute(this.expression, this.matcher);
	}

	@Test
	public void constructorNullExpression() {
		assertThatIllegalArgumentException().isThrownBy(() -> new MessageExpressionConfigAttribute(null, this.matcher));
	}

	@Test
	public void constructorNullMatcher() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new MessageExpressionConfigAttribute(this.expression, null));
	}

	@Test
	public void getAuthorizeExpression() {
		assertThat(this.attribute.getAuthorizeExpression()).isSameAs(this.expression);
	}

	@Test
	public void getAttribute() {
		assertThat(this.attribute.getAttribute()).isNull();
	}

	@Test
	public void toStringUsesExpressionString() {
		given(this.expression.getExpressionString()).willReturn("toString");
		assertThat(this.attribute.toString()).isEqualTo(this.expression.getExpressionString());
	}

	@Test
	public void postProcessContext() {
		SimpDestinationMessageMatcher matcher = new SimpDestinationMessageMatcher("/topics/{topic}/**");
		// @formatter:off
		Message<?> message = MessageBuilder.withPayload("M")
				.setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/topics/someTopic/sub1")
				.build();
		// @formatter:on
		EvaluationContext context = mock(EvaluationContext.class);
		this.attribute = new MessageExpressionConfigAttribute(this.expression, matcher);
		this.attribute.postProcess(context, message);
		verify(context).setVariable("topic", "someTopic");
	}

}
