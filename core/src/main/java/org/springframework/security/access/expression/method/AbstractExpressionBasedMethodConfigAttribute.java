package org.springframework.security.access.expression.method;

import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.Assert;

/**
 * Contains both filtering and authorization expression meta-data for Spring-EL based
 * access control.
 * <p>
 * Base class for pre or post-invocation phases of a method invocation.
 * <p>
 * Either filter or authorization expressions may be null, but not both.
 *
 * @author Luke Taylor
 * @since 3.0
 */
abstract class AbstractExpressionBasedMethodConfigAttribute implements ConfigAttribute {

	private final Expression filterExpression;

	private final Expression authorizeExpression;

	/**
	 * Parses the supplied expressions as Spring-EL.
	 */
	AbstractExpressionBasedMethodConfigAttribute(String filterExpression, String authorizeExpression)
			throws ParseException {
		Assert.isTrue(filterExpression != null || authorizeExpression != null,
				"Filter and authorization Expressions cannot both be null");
		SpelExpressionParser parser = new SpelExpressionParser();
		this.filterExpression = (filterExpression != null) ? parser.parseExpression(filterExpression) : null;
		this.authorizeExpression = (authorizeExpression != null) ? parser.parseExpression(authorizeExpression) : null;
	}

	AbstractExpressionBasedMethodConfigAttribute(Expression filterExpression, Expression authorizeExpression)
			throws ParseException {
		Assert.isTrue(filterExpression != null || authorizeExpression != null,
				"Filter and authorization Expressions cannot both be null");
		this.filterExpression = (filterExpression != null) ? filterExpression : null;
		this.authorizeExpression = (authorizeExpression != null) ? authorizeExpression : null;
	}

	Expression getFilterExpression() {
		return this.filterExpression;
	}

	Expression getAuthorizeExpression() {
		return this.authorizeExpression;
	}

	@Override
	public String getAttribute() {
		return null;
	}

}
