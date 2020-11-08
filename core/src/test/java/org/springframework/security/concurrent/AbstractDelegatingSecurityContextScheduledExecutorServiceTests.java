package org.springframework.security.concurrent;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThatObject;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Abstract class for testing {@link DelegatingSecurityContextScheduledExecutorService}
 * which allows customization of how
 * {@link DelegatingSecurityContextScheduledExecutorService} and its mocks are created.
 *
 * @author Rob Winch
 * @since 3.2
 * @see CurrentDelegatingSecurityContextScheduledExecutorServiceTests
 * @see ExplicitDelegatingSecurityContextScheduledExecutorServiceTests
 */
public abstract class AbstractDelegatingSecurityContextScheduledExecutorServiceTests
		extends AbstractDelegatingSecurityContextExecutorServiceTests {

	@Mock
	private ScheduledFuture<Object> expectedResult;

	private DelegatingSecurityContextScheduledExecutorService executor;

	@Before
	public final void setUpExecutor() {
		this.executor = create();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void scheduleRunnable() {
		given((ScheduledFuture<Object>) this.delegate.schedule(this.wrappedRunnable, 1, TimeUnit.SECONDS))
				.willReturn(this.expectedResult);
		ScheduledFuture<?> result = this.executor.schedule(this.runnable, 1, TimeUnit.SECONDS);
		assertThatObject(result).isEqualTo(this.expectedResult);
		verify(this.delegate).schedule(this.wrappedRunnable, 1, TimeUnit.SECONDS);
	}

	@Test
	public void scheduleCallable() {
		given(this.delegate.schedule(this.wrappedCallable, 1, TimeUnit.SECONDS)).willReturn(this.expectedResult);
		ScheduledFuture<Object> result = this.executor.schedule(this.callable, 1, TimeUnit.SECONDS);
		assertThatObject(result).isEqualTo(this.expectedResult);
		verify(this.delegate).schedule(this.wrappedCallable, 1, TimeUnit.SECONDS);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void scheduleAtFixedRate() {
		given((ScheduledFuture<Object>) this.delegate.scheduleAtFixedRate(this.wrappedRunnable, 1, 2, TimeUnit.SECONDS))
				.willReturn(this.expectedResult);
		ScheduledFuture<?> result = this.executor.scheduleAtFixedRate(this.runnable, 1, 2, TimeUnit.SECONDS);
		assertThatObject(result).isEqualTo(this.expectedResult);
		verify(this.delegate).scheduleAtFixedRate(this.wrappedRunnable, 1, 2, TimeUnit.SECONDS);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void scheduleWithFixedDelay() {
		given((ScheduledFuture<Object>) this.delegate.scheduleWithFixedDelay(this.wrappedRunnable, 1, 2,
				TimeUnit.SECONDS)).willReturn(this.expectedResult);
		ScheduledFuture<?> result = this.executor.scheduleWithFixedDelay(this.runnable, 1, 2, TimeUnit.SECONDS);
		assertThatObject(result).isEqualTo(this.expectedResult);
		verify(this.delegate).scheduleWithFixedDelay(this.wrappedRunnable, 1, 2, TimeUnit.SECONDS);
	}

	@Override
	protected abstract DelegatingSecurityContextScheduledExecutorService create();

}
