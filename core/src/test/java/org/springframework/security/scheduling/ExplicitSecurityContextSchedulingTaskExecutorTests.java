package org.springframework.security.scheduling;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests Explicitly specifying the {@link SecurityContext} on
 * {@link DelegatingSecurityContextSchedulingTaskExecutor}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class ExplicitSecurityContextSchedulingTaskExecutorTests
		extends AbstractSecurityContextSchedulingTaskExecutorTests {

	@Before
	public void setUp() throws Exception {
		explicitSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextSchedulingTaskExecutor create() {
		return new DelegatingSecurityContextSchedulingTaskExecutor(this.taskExecutorDelegate, this.securityContext);
	}

}
