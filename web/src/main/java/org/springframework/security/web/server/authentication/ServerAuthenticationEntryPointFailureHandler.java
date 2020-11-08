package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.util.Assert;

/**
 * Adapts a {@link ServerAuthenticationEntryPoint} into a
 * {@link ServerAuthenticationFailureHandler}
 *
 * @author Rob Winch
 * @since 5.0
 */
public class ServerAuthenticationEntryPointFailureHandler implements ServerAuthenticationFailureHandler {

	private final ServerAuthenticationEntryPoint authenticationEntryPoint;

	public ServerAuthenticationEntryPointFailureHandler(ServerAuthenticationEntryPoint authenticationEntryPoint) {
		Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
		return this.authenticationEntryPoint.commence(webFilterExchange.getExchange(), exception);
	}

}
