package org.springframework.security.web.server.authentication.logout;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;

/**
 * Strategy for when log out was successfully performed (typically after
 * {@link ServerLogoutHandler} is invoked).
 *
 * @author Rob Winch
 * @since 5.0
 * @see ServerLogoutHandler
 */
public interface ServerLogoutSuccessHandler {

	/**
	 * Invoked after log out was successful
	 * @param exchange the exchange
	 * @param authentication the {@link Authentication}
	 * @return a completion notification (success or error)
	 */
	Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication);

}
