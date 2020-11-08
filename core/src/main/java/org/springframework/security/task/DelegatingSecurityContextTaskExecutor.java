package org.springframework.security.task;

import org.springframework.core.task.TaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * An {@link TaskExecutor} which wraps each {@link Runnable} in a
 * {@link DelegatingSecurityContextRunnable}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class DelegatingSecurityContextTaskExecutor extends DelegatingSecurityContextExecutor implements TaskExecutor {

	/**
	 * Creates a new {@link DelegatingSecurityContextTaskExecutor} that uses the specified
	 * {@link SecurityContext}.
	 * @param delegateTaskExecutor the {@link TaskExecutor} to delegate to. Cannot be
	 * null.
	 * @param securityContext the {@link SecurityContext} to use for each
	 * {@link DelegatingSecurityContextRunnable}
	 */
	public DelegatingSecurityContextTaskExecutor(TaskExecutor delegateTaskExecutor, SecurityContext securityContext) {
		super(delegateTaskExecutor, securityContext);
	}

	/**
	 * Creates a new {@link DelegatingSecurityContextTaskExecutor} that uses the current
	 * {@link SecurityContext} from the {@link SecurityContextHolder}.
	 * @param delegate the {@link TaskExecutor} to delegate to. Cannot be null.
	 */
	public DelegatingSecurityContextTaskExecutor(TaskExecutor delegate) {
		this(delegate, null);
	}

}
