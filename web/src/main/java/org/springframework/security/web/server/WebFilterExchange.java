package org.springframework.security.web.server;

import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

/**
 * A composite of the {@link ServerWebExchange} and the {@link WebFilterChain}. This is
 * typically used as a value object for handling success and failures.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class WebFilterExchange {

	private final ServerWebExchange exchange;

	private final WebFilterChain chain;

	public WebFilterExchange(ServerWebExchange exchange, WebFilterChain chain) {
		Assert.notNull(exchange, "exchange cannot be null");
		Assert.notNull(chain, "chain cannot be null");
		this.exchange = exchange;
		this.chain = chain;
	}

	/**
	 * Get the exchange
	 * @return the exchange. Cannot be {@code null}
	 */
	public ServerWebExchange getExchange() {
		return this.exchange;
	}

	/**
	 * The filter chain
	 * @return the filter chain. Cannot be {@code null}
	 */
	public WebFilterChain getChain() {
		return this.chain;
	}

}
