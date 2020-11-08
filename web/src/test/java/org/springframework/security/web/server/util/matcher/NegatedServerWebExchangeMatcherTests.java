package org.springframework.security.web.server.util.matcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Tao Qian
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class NegatedServerWebExchangeMatcherTests {

	@Mock
	ServerWebExchange exchange;

	@Mock
	ServerWebExchangeMatcher matcher1;

	NegatedServerWebExchangeMatcher matcher;

	@Before
	public void setUp() {
		this.matcher = new NegatedServerWebExchangeMatcher(this.matcher1);
	}

	@Test
	public void matchesWhenFalseThenTrue() {
		given(this.matcher1.matches(this.exchange)).willReturn(ServerWebExchangeMatcher.MatchResult.notMatch());
		ServerWebExchangeMatcher.MatchResult matches = this.matcher.matches(this.exchange).block();
		assertThat(matches.isMatch()).isTrue();
		assertThat(matches.getVariables()).isEmpty();
		verify(this.matcher1).matches(this.exchange);
	}

	@Test
	public void matchesWhenTrueThenFalse() {
		given(this.matcher1.matches(this.exchange)).willReturn(ServerWebExchangeMatcher.MatchResult.match());
		ServerWebExchangeMatcher.MatchResult matches = this.matcher.matches(this.exchange).block();
		assertThat(matches.isMatch()).isFalse();
		assertThat(matches.getVariables()).isEmpty();
		verify(this.matcher1).matches(this.exchange);
	}

}
