package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;

/**
 * Handles authentication failure
 *
 * @author Rob Winch
 * @since 5.0
 */
public interface ServerAuthenticationFailureHandler {

	/**
	 * Invoked when authentication attempt fails
	 * @param webFilterExchange the exchange
	 * @param exception the reason authentication failed
	 * @return a completion notification (success or error)
	 */
	Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception);

}
