package org.springframework.security.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;

/**
 * An {@link AsyncTaskExecutor} which wraps each {@link Runnable} in a
 * {@link DelegatingSecurityContextRunnable} and each {@link Callable} in a
 * {@link DelegatingSecurityContextCallable}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class DelegatingSecurityContextAsyncTaskExecutor extends DelegatingSecurityContextTaskExecutor
		implements AsyncTaskExecutor {

	/**
	 * Creates a new {@link DelegatingSecurityContextAsyncTaskExecutor} that uses the
	 * specified {@link SecurityContext}.
	 * @param delegateAsyncTaskExecutor the {@link AsyncTaskExecutor} to delegate to.
	 * Cannot be null.
	 * @param securityContext the {@link SecurityContext} to use for each
	 * {@link DelegatingSecurityContextRunnable} and
	 * {@link DelegatingSecurityContextCallable}
	 */
	public DelegatingSecurityContextAsyncTaskExecutor(AsyncTaskExecutor delegateAsyncTaskExecutor,
			SecurityContext securityContext) {
		super(delegateAsyncTaskExecutor, securityContext);
	}

	/**
	 * Creates a new {@link DelegatingSecurityContextAsyncTaskExecutor} that uses the
	 * current {@link SecurityContext}.
	 * @param delegateAsyncTaskExecutor the {@link AsyncTaskExecutor} to delegate to.
	 * Cannot be null.
	 */
	public DelegatingSecurityContextAsyncTaskExecutor(AsyncTaskExecutor delegateAsyncTaskExecutor) {
		this(delegateAsyncTaskExecutor, null);
	}

	@Override
	public final void execute(Runnable task, long startTimeout) {
		getDelegate().execute(wrap(task), startTimeout);
	}

	@Override
	public final Future<?> submit(Runnable task) {
		return getDelegate().submit(wrap(task));
	}

	@Override
	public final <T> Future<T> submit(Callable<T> task) {
		return getDelegate().submit(wrap(task));
	}

	private AsyncTaskExecutor getDelegate() {
		return (AsyncTaskExecutor) getDelegateExecutor();
	}

}
