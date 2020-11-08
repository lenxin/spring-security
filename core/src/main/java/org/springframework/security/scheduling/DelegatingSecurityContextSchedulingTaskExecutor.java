package org.springframework.security.scheduling;

import java.util.concurrent.Callable;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

/**
 * An {@link SchedulingTaskExecutor} which wraps each {@link Runnable} in a
 * {@link DelegatingSecurityContextRunnable} and each {@link Callable} in a
 * {@link DelegatingSecurityContextCallable}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class DelegatingSecurityContextSchedulingTaskExecutor extends DelegatingSecurityContextAsyncTaskExecutor
		implements SchedulingTaskExecutor {

	/**
	 * Creates a new {@link DelegatingSecurityContextSchedulingTaskExecutor} that uses the
	 * specified {@link SecurityContext}.
	 * @param delegateSchedulingTaskExecutor the {@link SchedulingTaskExecutor} to
	 * delegate to. Cannot be null.
	 * @param securityContext the {@link SecurityContext} to use for each
	 * {@link DelegatingSecurityContextRunnable} and
	 * {@link DelegatingSecurityContextCallable}
	 */
	public DelegatingSecurityContextSchedulingTaskExecutor(SchedulingTaskExecutor delegateSchedulingTaskExecutor,
			SecurityContext securityContext) {
		super(delegateSchedulingTaskExecutor, securityContext);
	}

	/**
	 * Creates a new {@link DelegatingSecurityContextSchedulingTaskExecutor} that uses the
	 * current {@link SecurityContext}.
	 * @param delegateAsyncTaskExecutor the {@link AsyncTaskExecutor} to delegate to.
	 * Cannot be null.
	 */
	public DelegatingSecurityContextSchedulingTaskExecutor(SchedulingTaskExecutor delegateAsyncTaskExecutor) {
		this(delegateAsyncTaskExecutor, null);
	}

	@Override
	public boolean prefersShortLivedTasks() {
		return getDelegate().prefersShortLivedTasks();
	}

	private SchedulingTaskExecutor getDelegate() {
		return (SchedulingTaskExecutor) getDelegateExecutor();
	}

}
