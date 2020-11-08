package org.springframework.security.test.context.support;

import org.springframework.test.context.TestContext;

/**
 * Represents the events on the methods of
 * {@link org.springframework.test.context.TestExecutionListener}
 *
 * @author Rob Winch
 * @since 5.1
 */
public enum TestExecutionEvent {

	/**
	 * Associated to
	 * {@link org.springframework.test.context.TestExecutionListener#beforeTestMethod(TestContext)}
	 * event.
	 */
	TEST_METHOD,

	/**
	 * Associated to
	 * {@link org.springframework.test.context.TestExecutionListener#beforeTestExecution(TestContext)}
	 * event.
	 */
	TEST_EXECUTION

}
