package org.springframework.security.rsocket.util.matcher;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.rsocket.api.PayloadExchange;

/**
 * @author Rob Winch
 * @since 5.2
 */
public class PayloadExchangeAuthorizationContext {

	private final PayloadExchange exchange;

	private final Map<String, Object> variables;

	public PayloadExchangeAuthorizationContext(PayloadExchange exchange) {
		this(exchange, Collections.emptyMap());
	}

	public PayloadExchangeAuthorizationContext(PayloadExchange exchange, Map<String, Object> variables) {
		this.exchange = exchange;
		this.variables = variables;
	}

	public PayloadExchange getExchange() {
		return this.exchange;
	}

	public Map<String, Object> getVariables() {
		return Collections.unmodifiableMap(this.variables);
	}

}
