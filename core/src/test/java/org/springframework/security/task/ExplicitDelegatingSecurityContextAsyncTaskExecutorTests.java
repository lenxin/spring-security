package org.springframework.security.task;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests using an explicit {@link SecurityContext} on
 * {@link DelegatingSecurityContextAsyncTaskExecutor}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class ExplicitDelegatingSecurityContextAsyncTaskExecutorTests
		extends AbstractDelegatingSecurityContextAsyncTaskExecutorTests {

	@Before
	public void setUp() throws Exception {
		explicitSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextAsyncTaskExecutor create() {
		return new DelegatingSecurityContextAsyncTaskExecutor(this.taskExecutorDelegate, this.securityContext);
	}

}
