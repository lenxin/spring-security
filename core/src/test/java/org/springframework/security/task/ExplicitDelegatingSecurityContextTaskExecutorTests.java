package org.springframework.security.task;

import java.util.concurrent.Executor;

import org.junit.Before;
import org.mockito.Mock;

import org.springframework.core.task.TaskExecutor;
import org.springframework.security.concurrent.AbstractDelegatingSecurityContextExecutorTests;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.core.context.SecurityContext;

/**
 * Tests using an explicit {@link SecurityContext} on
 * {@link DelegatingSecurityContextExecutor}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class ExplicitDelegatingSecurityContextTaskExecutorTests extends AbstractDelegatingSecurityContextExecutorTests {

	@Mock
	private TaskExecutor taskExecutorDelegate;

	@Before
	public void setUp() throws Exception {
		explicitSecurityContextPowermockSetup();
	}

	@Override
	protected Executor getExecutor() {
		return this.taskExecutorDelegate;
	}

	@Override
	protected DelegatingSecurityContextExecutor create() {
		return new DelegatingSecurityContextTaskExecutor(this.taskExecutorDelegate, this.securityContext);
	}

}
