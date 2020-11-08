package org.springframework.security.web.server.authentication.logout;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.publisher.PublisherProbe;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Eric Deandrea
 * @since 5.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DelegatingServerLogoutHandlerTests {

	@Mock
	private ServerLogoutHandler delegate1;

	@Mock
	private ServerLogoutHandler delegate2;

	private PublisherProbe<Void> delegate1Result = PublisherProbe.empty();

	private PublisherProbe<Void> delegate2Result = PublisherProbe.empty();

	@Mock
	private WebFilterExchange exchange;

	@Mock
	private Authentication authentication;

	@Before
	public void setup() {
		given(this.delegate1.logout(any(WebFilterExchange.class), any(Authentication.class)))
				.willReturn(this.delegate1Result.mono());
		given(this.delegate2.logout(any(WebFilterExchange.class), any(Authentication.class)))
				.willReturn(this.delegate2Result.mono());
	}

	@Test
	public void constructorWhenNullVargsThenIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingServerLogoutHandler((ServerLogoutHandler[]) null))
				.withMessage("delegates cannot be null or empty").withNoCause();
	}

	@Test
	public void constructorWhenNullListThenIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingServerLogoutHandler((List<ServerLogoutHandler>) null))
				.withMessage("delegates cannot be null or empty").withNoCause();
	}

	@Test
	public void constructorWhenEmptyThenIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingServerLogoutHandler(new ServerLogoutHandler[0]))
				.withMessage("delegates cannot be null or empty").withNoCause();
	}

	@Test
	public void logoutWhenSingleThenExecuted() {
		DelegatingServerLogoutHandler handler = new DelegatingServerLogoutHandler(this.delegate1);
		handler.logout(this.exchange, this.authentication).block();
		this.delegate1Result.assertWasSubscribed();
	}

	@Test
	public void logoutWhenMultipleThenExecuted() {
		DelegatingServerLogoutHandler handler = new DelegatingServerLogoutHandler(this.delegate1, this.delegate2);
		handler.logout(this.exchange, this.authentication).block();
		this.delegate1Result.assertWasSubscribed();
		this.delegate2Result.assertWasSubscribed();
	}

	@Test
	public void logoutSequential() throws Exception {
		AtomicBoolean slowDone = new AtomicBoolean();
		CountDownLatch latch = new CountDownLatch(1);
		ServerLogoutHandler slow = (exchange, authentication) -> Mono.delay(Duration.ofMillis(100))
				.doOnSuccess((__) -> slowDone.set(true)).then();
		ServerLogoutHandler second = (exchange, authentication) -> Mono.fromRunnable(() -> {
			latch.countDown();
			assertThat(slowDone.get()).describedAs("ServerLogoutHandler should be executed sequentially").isTrue();
		});
		DelegatingServerLogoutHandler handler = new DelegatingServerLogoutHandler(slow, second);
		handler.logout(this.exchange, this.authentication).block();
		assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue();
	}

}
