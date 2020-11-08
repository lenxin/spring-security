package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;

/**
 * Handles authentication success
 *
 * @author Rob Winch
 * @since 5.0
 */
public interface ServerAuthenticationSuccessHandler {

	/**
	 * Invoked when the application authenticates successfully
	 * @param webFilterExchange the exchange
	 * @param authentication the {@link Authentication}
	 * @return a completion notification (success or error)
	 */
	Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication);

}
