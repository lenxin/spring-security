package org.springframework.security.web.server.authorization;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * A {@link ServerAccessDeniedHandler} which delegates to multiple
 * {@link ServerAccessDeniedHandler}s based on a {@link ServerWebExchangeMatcher}
 *
 * @author Josh Cummings
 * @since 5.1
 */
public class ServerWebExchangeDelegatingServerAccessDeniedHandler implements ServerAccessDeniedHandler {

	private final List<DelegateEntry> handlers;

	private ServerAccessDeniedHandler defaultHandler = (exchange, ex) -> {
		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		return exchange.getResponse().setComplete();
	};

	/**
	 * Creates a new instance
	 * @param handlers a list of {@link ServerWebExchangeMatcher}/
	 * {@link ServerAccessDeniedHandler} pairs that should be used. Each is considered in
	 * the order they are specified and only the first {@link ServerAccessDeniedHandler}
	 * is used. If none match, then the default {@link ServerAccessDeniedHandler} is used.
	 */
	public ServerWebExchangeDelegatingServerAccessDeniedHandler(DelegateEntry... handlers) {
		this(Arrays.asList(handlers));
	}

	/**
	 * Creates a new instance
	 * @param handlers a list of {@link ServerWebExchangeMatcher}/
	 * {@link ServerAccessDeniedHandler} pairs that should be used. Each is considered in
	 * the order they are specified and only the first {@link ServerAccessDeniedHandler}
	 * is used. If none match, then the default {@link ServerAccessDeniedHandler} is used.
	 */
	public ServerWebExchangeDelegatingServerAccessDeniedHandler(List<DelegateEntry> handlers) {
		Assert.notEmpty(handlers, "handlers cannot be null");
		this.handlers = handlers;
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
		return Flux.fromIterable(this.handlers).filterWhen((entry) -> isMatch(exchange, entry)).next()
				.map(DelegateEntry::getAccessDeniedHandler).defaultIfEmpty(this.defaultHandler)
				.flatMap((handler) -> handler.handle(exchange, denied));
	}

	/**
	 * Use this {@link ServerAccessDeniedHandler} when no {@link ServerWebExchangeMatcher}
	 * matches.
	 * @param accessDeniedHandler - the default {@link ServerAccessDeniedHandler} to use
	 */
	public void setDefaultAccessDeniedHandler(ServerAccessDeniedHandler accessDeniedHandler) {
		Assert.notNull(accessDeniedHandler, "accessDeniedHandler cannot be null");
		this.defaultHandler = accessDeniedHandler;
	}

	private Mono<Boolean> isMatch(ServerWebExchange exchange, DelegateEntry entry) {
		ServerWebExchangeMatcher matcher = entry.getMatcher();
		return matcher.matches(exchange).map(ServerWebExchangeMatcher.MatchResult::isMatch);
	}

	public static class DelegateEntry {

		private final ServerWebExchangeMatcher matcher;

		private final ServerAccessDeniedHandler accessDeniedHandler;

		public DelegateEntry(ServerWebExchangeMatcher matcher, ServerAccessDeniedHandler accessDeniedHandler) {
			this.matcher = matcher;
			this.accessDeniedHandler = accessDeniedHandler;
		}

		public ServerWebExchangeMatcher getMatcher() {
			return this.matcher;
		}

		public ServerAccessDeniedHandler getAccessDeniedHandler() {
			return this.accessDeniedHandler;
		}

	}

}
