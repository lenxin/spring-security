package org.springframework.security.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.verify;

/**
 * Abstract class for testing {@link DelegatingSecurityContextExecutor} which allows
 * customization of how {@link DelegatingSecurityContextExecutor} and its mocks are
 * created.
 *
 * @author Rob Winch
 * @since 3.2
 * @see CurrentDelegatingSecurityContextExecutorTests
 * @see ExplicitDelegatingSecurityContextExecutorTests
 */
public abstract class AbstractDelegatingSecurityContextExecutorTests
		extends AbstractDelegatingSecurityContextTestSupport {

	@Mock
	protected ScheduledExecutorService delegate;

	private DelegatingSecurityContextExecutor executor;

	@Test
	public void constructorNullDelegate() {
		assertThatIllegalArgumentException().isThrownBy(() -> new DelegatingSecurityContextExecutor(null));
	}

	@Test
	public void execute() {
		this.executor = create();
		this.executor.execute(this.runnable);
		verify(getExecutor()).execute(this.wrappedRunnable);
	}

	protected Executor getExecutor() {
		return this.delegate;
	}

	protected abstract DelegatingSecurityContextExecutor create();

}
