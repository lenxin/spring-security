package org.springframework.security.web.server.csrf;

import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;

/**
 * An API to allow changing the method in which the expected {@link CsrfToken} is
 * associated to the {@link ServerWebExchange}. For example, it may be stored in
 * {@link org.springframework.web.server.WebSession}.
 *
 * @author Rob Winch
 * @since 5.0
 * @see WebSessionServerCsrfTokenRepository
 *
 */
public interface ServerCsrfTokenRepository {

	/**
	 * Generates a {@link CsrfToken}
	 * @param exchange the {@link ServerWebExchange} to use
	 * @return the {@link CsrfToken} that was generated. Cannot be null.
	 */
	Mono<CsrfToken> generateToken(ServerWebExchange exchange);

	/**
	 * Saves the {@link CsrfToken} using the {@link ServerWebExchange}. If the
	 * {@link CsrfToken} is null, it is the same as deleting it.
	 * @param exchange the {@link ServerWebExchange} to use
	 * @param token the {@link CsrfToken} to save or null to delete
	 */
	Mono<Void> saveToken(ServerWebExchange exchange, CsrfToken token);

	/**
	 * Loads the expected {@link CsrfToken} from the {@link ServerWebExchange}
	 * @param exchange the {@link ServerWebExchange} to use
	 * @return the {@link CsrfToken} or null if none exists
	 */
	Mono<CsrfToken> loadToken(ServerWebExchange exchange);

}
