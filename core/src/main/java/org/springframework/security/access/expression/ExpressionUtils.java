package org.springframework.security.access.expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;

public final class ExpressionUtils {

	private ExpressionUtils() {
	}

	public static boolean evaluateAsBoolean(Expression expr, EvaluationContext ctx) {
		try {
			return expr.getValue(ctx, Boolean.class);
		}
		catch (EvaluationException ex) {
			throw new IllegalArgumentException("Failed to evaluate expression '" + expr.getExpressionString() + "'",
					ex);
		}
	}

}
