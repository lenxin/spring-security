package org.springframework.security.web.access.expression;

import org.springframework.expression.EvaluationContext;

/**
 *
 * /** Allows post processing the {@link EvaluationContext}
 *
 * <p>
 * This API is intentionally kept package scope as it may evolve over time.
 * </p>
 *
 * @param <I> the invocation to use for post processing
 * @author Rob Winch
 * @since 4.1
 */
interface EvaluationContextPostProcessor<I> {

	/**
	 * Allows post processing of the {@link EvaluationContext}. Implementations may return
	 * a new instance of {@link EvaluationContext} or modify the {@link EvaluationContext}
	 * that was passed in.
	 * @param context the original {@link EvaluationContext}
	 * @param invocation the security invocation object (i.e. FilterInvocation)
	 * @return the upated context.
	 */
	EvaluationContext postProcess(EvaluationContext context, I invocation);

}
