package org.springframework.security.web.server.context;

import reactor.core.publisher.Mono;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;

/**
 * Strategy used for persisting a {@link SecurityContext} between requests.
 *
 * @author Rob Winch
 * @since 5.0
 * @see ReactorContextWebFilter
 */
public interface ServerSecurityContextRepository {

	/**
	 * Saves the SecurityContext
	 * @param exchange the exchange to associate to the SecurityContext
	 * @param context the SecurityContext to save
	 * @return a completion notification (success or error)
	 */
	Mono<Void> save(ServerWebExchange exchange, SecurityContext context);

	/**
	 * Loads the SecurityContext associated with the {@link ServerWebExchange}
	 * @param exchange the exchange to look up the {@link SecurityContext}
	 * @return the {@link SecurityContext} to lookup or empty if not found. Never null
	 */
	Mono<SecurityContext> load(ServerWebExchange exchange);

}
