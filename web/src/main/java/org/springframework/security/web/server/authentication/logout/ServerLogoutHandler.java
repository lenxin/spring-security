package org.springframework.security.web.server.authentication.logout;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;

/**
 * Handles log out
 *
 * @author Rob Winch
 * @since 5.0
 * @see ServerLogoutSuccessHandler
 */
public interface ServerLogoutHandler {

	/**
	 * Invoked when log out is requested
	 * @param exchange the exchange
	 * @param authentication the {@link Authentication}
	 * @return a completion notification (success or error)
	 */
	Mono<Void> logout(WebFilterExchange exchange, Authentication authentication);

}
