package org.springframework.security.web.server.context;

import reactor.core.publisher.Mono;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;

/**
 * A do nothing implementation of {@link ServerSecurityContextRepository}. Used in
 * stateless applications.
 *
 * @author Rob Winch
 * @since 5.0
 */
public final class NoOpServerSecurityContextRepository implements ServerSecurityContextRepository {

	private static final NoOpServerSecurityContextRepository INSTANCE = new NoOpServerSecurityContextRepository();

	private NoOpServerSecurityContextRepository() {
	}

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.empty();
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		return Mono.empty();
	}

	public static NoOpServerSecurityContextRepository getInstance() {
		return INSTANCE;
	}

}
