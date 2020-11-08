package org.springframework.security.web.access.expression;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.expression.EvaluationContext;
import org.springframework.security.web.FilterInvocation;

/**
 * Exposes URI template variables as variables on the {@link EvaluationContext}. For
 * example, the pattern "/user/{username}/**" would expose a variable named username based
 * on the current URI.
 *
 * <p>
 * NOTE: This API is intentionally kept package scope as it may change in the future. It
 * may be nice to allow users to augment expressions and queries
 * </p>
 *
 * @author Rob Winch
 * @since 4.1
 */
abstract class AbstractVariableEvaluationContextPostProcessor
		implements EvaluationContextPostProcessor<FilterInvocation> {

	@Override
	public final EvaluationContext postProcess(EvaluationContext context, FilterInvocation invocation) {
		return new VariableEvaluationContext(context, invocation.getHttpRequest());
	}

	abstract Map<String, String> extractVariables(HttpServletRequest request);

	/**
	 * {@link DelegatingEvaluationContext} to expose variable.
	 */
	class VariableEvaluationContext extends DelegatingEvaluationContext {

		private final HttpServletRequest request;

		private Map<String, String> variables;

		VariableEvaluationContext(EvaluationContext delegate, HttpServletRequest request) {
			super(delegate);
			this.request = request;
		}

		@Override
		public Object lookupVariable(String name) {
			Object result = super.lookupVariable(name);
			if (result != null) {
				return result;
			}
			if (this.variables == null) {
				this.variables = extractVariables(this.request);
			}
			return this.variables.get(name);
		}

	}

}
