package org.springframework.security.task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.security.concurrent.AbstractDelegatingSecurityContextExecutorTests;

import static org.mockito.Mockito.verify;

/**
 * Abstract class for testing {@link DelegatingSecurityContextAsyncTaskExecutor} which
 * allows customization of how {@link DelegatingSecurityContextAsyncTaskExecutor} and its
 * mocks are created.
 *
 * @author Rob Winch
 * @since 3.2
 * @see CurrentDelegatingSecurityContextAsyncTaskExecutorTests
 * @see ExplicitDelegatingSecurityContextAsyncTaskExecutorTests
 */
public abstract class AbstractDelegatingSecurityContextAsyncTaskExecutorTests
		extends AbstractDelegatingSecurityContextExecutorTests {

	@Mock
	protected AsyncTaskExecutor taskExecutorDelegate;

	private DelegatingSecurityContextAsyncTaskExecutor executor;

	@Before
	public final void setUpExecutor() {
		this.executor = create();
	}

	@Test
	public void executeStartTimeout() {
		this.executor.execute(this.runnable, 1);
		verify(getExecutor()).execute(this.wrappedRunnable, 1);
	}

	@Test
	public void submit() {
		this.executor.submit(this.runnable);
		verify(getExecutor()).submit(this.wrappedRunnable);
	}

	@Test
	public void submitCallable() {
		this.executor.submit(this.callable);
		verify(getExecutor()).submit(this.wrappedCallable);
	}

	@Override
	protected AsyncTaskExecutor getExecutor() {
		return this.taskExecutorDelegate;
	}

	@Override
	protected abstract DelegatingSecurityContextAsyncTaskExecutor create();

}
