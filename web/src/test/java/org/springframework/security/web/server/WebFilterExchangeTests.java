package org.springframework.security.web.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class WebFilterExchangeTests {

	@Mock
	private ServerWebExchange exchange;

	@Mock
	private WebFilterChain chain;

	@Test
	public void constructorServerWebExchangeWebFilterChainWhenExchangeNullThenException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new WebFilterExchange(null, this.chain));
	}

	@Test
	public void constructorServerWebExchangeWebFilterChainWhenChainNullThenException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new WebFilterExchange(this.exchange, null));
	}

	@Test
	public void getExchange() {
		WebFilterExchange filterExchange = new WebFilterExchange(this.exchange, this.chain);
		assertThat(filterExchange.getExchange()).isEqualTo(this.exchange);
	}

	@Test
	public void getChain() {
		WebFilterExchange filterExchange = new WebFilterExchange(this.exchange, this.chain);
		assertThat(filterExchange.getChain()).isEqualTo(this.chain);
	}

}
