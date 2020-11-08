package org.springframework.security.test.context.support;

import org.junit.Test;

import org.springframework.core.annotation.AnnotatedElementUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class WithMockUserTests {

	@Test
	public void defaults() {
		WithMockUser mockUser = AnnotatedElementUtils.findMergedAnnotation(Annotated.class, WithMockUser.class);
		assertThat(mockUser.value()).isEqualTo("user");
		assertThat(mockUser.username()).isEmpty();
		assertThat(mockUser.password()).isEqualTo("password");
		assertThat(mockUser.roles()).containsOnly("USER");
		assertThat(mockUser.setupBefore()).isEqualByComparingTo(TestExecutionEvent.TEST_METHOD);
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

	@WithMockUser
	private class Annotated {

	}

	@WithMockUser(setupBefore = TestExecutionEvent.TEST_METHOD)
	private class SetupExplicit {

	}

	@WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
	private class SetupOverridden {

	}

}
