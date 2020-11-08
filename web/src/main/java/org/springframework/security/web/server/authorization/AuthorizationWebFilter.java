package org.springframework.security.web.server.authorization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Mono;

import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * @author Rob Winch
 * @author Mathieu Ouellet
 * @since 5.0
 */
public class AuthorizationWebFilter implements WebFilter {

	private static final Log logger = LogFactory.getLog(AuthorizationWebFilter.class);

	private ReactiveAuthorizationManager<? super ServerWebExchange> authorizationManager;

	public AuthorizationWebFilter(ReactiveAuthorizationManager<? super ServerWebExchange> authorizationManager) {
		this.authorizationManager = authorizationManager;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return ReactiveSecurityContextHolder.getContext().filter((c) -> c.getAuthentication() != null)
				.map(SecurityContext::getAuthentication)
				.as((authentication) -> this.authorizationManager.verify(authentication, exchange))
				.doOnSuccess((it) -> logger.debug("Authorization successful"))
				.doOnError(AccessDeniedException.class,
						(ex) -> logger.debug(LogMessage.format("Authorization failed: %s", ex.getMessage())))
				.switchIfEmpty(chain.filter(exchange));
	}

}
