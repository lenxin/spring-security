package org.springframework.security.messaging.access.expression;

import java.util.Collection;

import org.springframework.expression.EvaluationContext;
import org.springframework.messaging.Message;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Voter which handles {@link Message} authorisation decisions. If a
 * {@link MessageExpressionConfigAttribute} is found, then its expression is evaluated. If
 * true, {@code ACCESS_GRANTED} is returned. If false, {@code ACCESS_DENIED} is returned.
 * If no {@code MessageExpressionConfigAttribute} is found, then {@code ACCESS_ABSTAIN} is
 * returned.
 *
 * @author Rob Winch
 * @author Daniel Bustamante Ospina
 * @since 4.0
 */
public class MessageExpressionVoter<T> implements AccessDecisionVoter<Message<T>> {

	private SecurityExpressionHandler<Message<T>> expressionHandler = new DefaultMessageSecurityExpressionHandler<>();

	@Override
	public int vote(Authentication authentication, Message<T> message, Collection<ConfigAttribute> attributes) {
		Assert.notNull(authentication, "authentication must not be null");
		Assert.notNull(message, "message must not be null");
		Assert.notNull(attributes, "attributes must not be null");
		MessageExpressionConfigAttribute attr = findConfigAttribute(attributes);
		if (attr == null) {
			return ACCESS_ABSTAIN;
		}
		EvaluationContext ctx = this.expressionHandler.createEvaluationContext(authentication, message);
		ctx = attr.postProcess(ctx, message);
		return ExpressionUtils.evaluateAsBoolean(attr.getAuthorizeExpression(), ctx) ? ACCESS_GRANTED : ACCESS_DENIED;
	}

	private MessageExpressionConfigAttribute findConfigAttribute(Collection<ConfigAttribute> attributes) {
		for (ConfigAttribute attribute : attributes) {
			if (attribute instanceof MessageExpressionConfigAttribute) {
				return (MessageExpressionConfigAttribute) attribute;
			}
		}
		return null;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return attribute instanceof MessageExpressionConfigAttribute;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Message.class.isAssignableFrom(clazz);
	}

	public void setExpressionHandler(SecurityExpressionHandler<Message<T>> expressionHandler) {
		Assert.notNull(expressionHandler, "expressionHandler cannot be null");
		this.expressionHandler = expressionHandler;
	}

}
