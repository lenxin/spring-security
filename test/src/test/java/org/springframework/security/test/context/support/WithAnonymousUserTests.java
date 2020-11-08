package org.springframework.security.test.context.support;

import org.junit.Test;

import org.springframework.core.annotation.AnnotatedElementUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class WithAnonymousUserTests {

	@Test
	public void defaults() {
		WithSecurityContext context = AnnotatedElementUtils.findMergedAnnotation(Annotated.class,
				WithSecurityContext.class);
		assertThat(context.setupBefore()).isEqualTo(TestExecutionEvent.TEST_METHOD);
	}

	@Test
	public void findMergedAnnotationWhenSetupExplicitThenOverridden() {
		WithSecurityContext context = AnnotatedElementUtils.findMergedAnnotation(SetupExplicit.class,
				WithSecurityContext.class);
		assertThat(context.setupBefore()).isEqualTo(TestExecutionEvent.TEST_METHOD);
	}

	@Test
	public void findMergedAnnotationWhenSetupOverriddenThenOverridden() {
		WithSecurityContext context = AnnotatedElementUtils.findMergedAnnotation(SetupOverridden.class,
				WithSecurityContext.class);
		assertThat(context.setupBefore()).isEqualTo(TestExecutionEvent.TEST_EXECUTION);
	}

	@WithAnonymousUser
	private class Annotated {

	}

	@WithAnonymousUser(setupBefore = TestExecutionEvent.TEST_METHOD)
	private class SetupExplicit {

	}

	@WithAnonymousUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
	private class SetupOverridden {

	}

}
