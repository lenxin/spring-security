package org.springframework.security.web.server.util.matcher;

import org.junit.Test;

import org.springframework.http.HttpMethod;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class ServerWebExchangeMatchersTests {

	ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());

	@Test
	public void pathMatchersWhenSingleAndSamePatternThenMatches() {
		assertThat(ServerWebExchangeMatchers.pathMatchers("/").matches(this.exchange).block().isMatch()).isTrue();
	}

	@Test
	public void pathMatchersWhenSingleAndSamePatternAndMethodThenMatches() {
		assertThat(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/").matches(this.exchange).block().isMatch())
				.isTrue();
	}

	@Test
	public void pathMatchersWhenSingleAndSamePatternAndDiffMethodThenDoesNotMatch() {
		assertThat(
				ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/").matches(this.exchange).block().isMatch())
						.isFalse();
	}

	@Test
	public void pathMatchersWhenSingleAndDifferentPatternThenDoesNotMatch() {
		assertThat(ServerWebExchangeMatchers.pathMatchers("/foobar").matches(this.exchange).block().isMatch())
				.isFalse();
	}

	@Test
	public void pathMatchersWhenMultiThenMatches() {
		assertThat(ServerWebExchangeMatchers.pathMatchers("/foobar", "/").matches(this.exchange).block().isMatch())
				.isTrue();
	}

	@Test
	public void anyExchangeWhenMockThenMatches() {
		ServerWebExchange mockExchange = mock(ServerWebExchange.class);
		assertThat(ServerWebExchangeMatchers.anyExchange().matches(mockExchange).block().isMatch()).isTrue();
		verifyZeroInteractions(mockExchange);
	}

	/**
	 * If a LinkedMap is used and anyRequest equals anyRequest then the following is
	 * added: anyRequest() -> authenticated() pathMatchers("/admin/**") ->
	 * hasRole("ADMIN") anyRequest() -> permitAll
	 *
	 * will result in the first entry being overridden
	 */
	@Test
	public void anyExchangeWhenTwoCreatedThenDifferentToPreventIssuesInMap() {
		assertThat(ServerWebExchangeMatchers.anyExchange()).isNotEqualTo(ServerWebExchangeMatchers.anyExchange());
	}

}
