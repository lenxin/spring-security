package org.springframework.security.web.server.authorization;

import reactor.core.publisher.Mono;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Rob Winch
 * @since 5.0
 */
public interface ServerAccessDeniedHandler {

	Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied);

}
