package org.springframework.security.web.access.expression;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterInvocation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
public class AbstractVariableEvaluationContextPostProcessorTests {

	static final String KEY = "a";
	static final String VALUE = "b";

	VariableEvaluationContextPostProcessor processor;

	FilterInvocation invocation;

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	EvaluationContext context;

	@Before
	public void setup() {
		this.processor = new VariableEvaluationContextPostProcessor();
		this.request = new MockHttpServletRequest();
		this.request.setServletPath("/");
		this.response = new MockHttpServletResponse();
		this.invocation = new FilterInvocation(this.request, this.response, new MockFilterChain());
		this.context = new StandardEvaluationContext();
	}

	@Test
	public void extractVariables() {
		this.context = this.processor.postProcess(this.context, this.invocation);
		assertThat(this.context.lookupVariable(KEY)).isEqualTo(VALUE);
	}

	@Test
	public void extractVariablesOnlyUsedOnce() {
		this.context = this.processor.postProcess(this.context, this.invocation);
		assertThat(this.context.lookupVariable(KEY)).isEqualTo(VALUE);
		this.processor.results = Collections.emptyMap();
		assertThat(this.context.lookupVariable(KEY)).isEqualTo(VALUE);
	}

	static class VariableEvaluationContextPostProcessor extends AbstractVariableEvaluationContextPostProcessor {

		Map<String, String> results = Collections.singletonMap(KEY, VALUE);

		@Override
		protected Map<String, String> extractVariables(HttpServletRequest request) {
			return this.results;
		}

	}

}
