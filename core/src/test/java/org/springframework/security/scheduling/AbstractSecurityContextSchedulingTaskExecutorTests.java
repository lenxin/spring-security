package org.springframework.security.scheduling;

import org.junit.Test;
import org.mockito.Mock;

import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.security.task.AbstractDelegatingSecurityContextAsyncTaskExecutorTests;

import static org.mockito.Mockito.verify;

/**
 * Abstract class for testing {@link DelegatingSecurityContextSchedulingTaskExecutor}
 * which allows customization of how
 * {@link DelegatingSecurityContextSchedulingTaskExecutor} and its mocks are created.
 *
 * @author Rob Winch
 * @since 3.2
 * @see CurrentSecurityContextSchedulingTaskExecutorTests
 * @see ExplicitSecurityContextSchedulingTaskExecutorTests
 */
public abstract class AbstractSecurityContextSchedulingTaskExecutorTests
		extends AbstractDelegatingSecurityContextAsyncTaskExecutorTests {

	@Mock
	protected SchedulingTaskExecutor taskExecutorDelegate;

	private DelegatingSecurityContextSchedulingTaskExecutor executor;

	@Test
	public void prefersShortLivedTasks() {
		this.executor = create();
		this.executor.prefersShortLivedTasks();
		verify(this.taskExecutorDelegate).prefersShortLivedTasks();
	}

	@Override
	protected SchedulingTaskExecutor getExecutor() {
		return this.taskExecutorDelegate;
	}

	@Override
	protected abstract DelegatingSecurityContextSchedulingTaskExecutor create();

}
