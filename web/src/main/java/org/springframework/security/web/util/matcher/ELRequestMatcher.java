package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;

/**
 * A RequestMatcher implementation which uses a SpEL expression
 *
 * <p>
 * With the default EvaluationContext ({@link ELRequestMatcherContext}) you can use
 * <code>hasIpAdress()</code> and <code>hasHeader()</code>
 * </p>
 *
 * <p>
 * See {@link DelegatingAuthenticationEntryPoint} for an example configuration.
 * </p>
 *
 * @author Mike Wiesner
 * @since 3.0.2
 */
public class ELRequestMatcher implements RequestMatcher {

	private final Expression expression;

	public ELRequestMatcher(String el) {
		SpelExpressionParser parser = new SpelExpressionParser();
		this.expression = parser.parseExpression(el);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		EvaluationContext context = createELContext(request);
		return this.expression.getValue(context, Boolean.class);
	}

	/**
	 * Subclasses can override this methode if they want to use a different EL root
	 * context
	 * @return EL root context which is used to evaluate the expression
	 */
	public EvaluationContext createELContext(HttpServletRequest request) {
		return new StandardEvaluationContext(new ELRequestMatcherContext(request));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EL [el=\"").append(this.expression.getExpressionString()).append("\"");
		sb.append("]");
		return sb.toString();
	}

}
