package org.springframework.security.web.access.expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

/**
 * Simple expression configuration attribute for use in web request authorizations.
 *
 * @author Luke Taylor
 * @since 3.0
 */
class WebExpressionConfigAttribute implements ConfigAttribute, EvaluationContextPostProcessor<FilterInvocation> {

	private final Expression authorizeExpression;

	private final EvaluationContextPostProcessor<FilterInvocation> postProcessor;

	WebExpressionConfigAttribute(Expression authorizeExpression,
			EvaluationContextPostProcessor<FilterInvocation> postProcessor) {
		this.authorizeExpression = authorizeExpression;
		this.postProcessor = postProcessor;
	}

	Expression getAuthorizeExpression() {
		return this.authorizeExpression;
	}

	@Override
	public EvaluationContext postProcess(EvaluationContext context, FilterInvocation fi) {
		return (this.postProcessor != null) ? this.postProcessor.postProcess(context, fi) : context;
	}

	@Override
	public String getAttribute() {
		return null;
	}

	@Override
	public String toString() {
		return this.authorizeExpression.getExpressionString();
	}

}
