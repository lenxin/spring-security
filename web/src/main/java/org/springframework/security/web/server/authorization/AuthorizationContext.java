package org.springframework.security.web.server.authorization;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class AuthorizationContext {

	private final ServerWebExchange exchange;

	private final Map<String, Object> variables;

	public AuthorizationContext(ServerWebExchange exchange) {
		this(exchange, Collections.emptyMap());
	}

	public AuthorizationContext(ServerWebExchange exchange, Map<String, Object> variables) {
		this.exchange = exchange;
		this.variables = variables;
	}

	public ServerWebExchange getExchange() {
		return this.exchange;
	}

	public Map<String, Object> getVariables() {
		return Collections.unmodifiableMap(this.variables);
	}

}
