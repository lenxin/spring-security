package org.springframework.security.messaging.access.expression;

import java.util.Map;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.messaging.Message;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.messaging.util.matcher.MessageMatcher;
import org.springframework.security.messaging.util.matcher.SimpDestinationMessageMatcher;
import org.springframework.util.Assert;

/**
 * Simple expression configuration attribute for use in {@link Message} authorizations.
 *
 * @since 4.0
 * @author Rob Winch
 * @author Daniel Bustamante Ospina
 */
@SuppressWarnings("serial")
class MessageExpressionConfigAttribute implements ConfigAttribute, EvaluationContextPostProcessor<Message<?>> {

	private final Expression authorizeExpression;

	private final MessageMatcher<?> matcher;

	/**
	 * Creates a new instance
	 * @param authorizeExpression the {@link Expression} to use. Cannot be null
	 * @param matcher the {@link MessageMatcher} used to match the messages.
	 */
	MessageExpressionConfigAttribute(Expression authorizeExpression, MessageMatcher<?> matcher) {
		Assert.notNull(authorizeExpression, "authorizeExpression cannot be null");
		Assert.notNull(matcher, "matcher cannot be null");
		this.authorizeExpression = authorizeExpression;
		this.matcher = matcher;
	}

	Expression getAuthorizeExpression() {
		return this.authorizeExpression;
	}

	@Override
	public String getAttribute() {
		return null;
	}

	@Override
	public String toString() {
		return this.authorizeExpression.getExpressionString();
	}

	@Override
	public EvaluationContext postProcess(EvaluationContext ctx, Message<?> message) {
		if (this.matcher instanceof SimpDestinationMessageMatcher) {
			Map<String, String> variables = ((SimpDestinationMessageMatcher) this.matcher)
					.extractPathVariables(message);
			for (Map.Entry<String, String> entry : variables.entrySet()) {
				ctx.setVariable(entry.getKey(), entry.getValue());
			}
		}
		return ctx;
	}

}
