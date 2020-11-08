package org.springframework.security.test.context.support;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.Assert;

/**
 * @author Rob Winch
 * @since 5.0
 */
class DelegatingTestExecutionListener extends AbstractTestExecutionListener {

	private final TestExecutionListener delegate;

	DelegatingTestExecutionListener(TestExecutionListener delegate) {
		Assert.notNull(delegate, "delegate cannot be null");
		this.delegate = delegate;
	}

	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		this.delegate.beforeTestClass(testContext);
	}

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		this.delegate.prepareTestInstance(testContext);
	}

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		this.delegate.beforeTestMethod(testContext);
	}

	@Override
	public void beforeTestExecution(TestContext testContext) throws Exception {
		this.delegate.beforeTestExecution(testContext);
	}

	@Override
	public void afterTestExecution(TestContext testContext) throws Exception {
		this.delegate.afterTestExecution(testContext);
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		this.delegate.afterTestMethod(testContext);
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		this.delegate.afterTestClass(testContext);
	}

}
