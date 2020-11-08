package org.springframework.security.rsocket.authorization;

import reactor.core.publisher.Mono;

import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.api.PayloadInterceptor;
import org.springframework.security.rsocket.api.PayloadInterceptorChain;
import org.springframework.util.Assert;

/**
 * Provides authorization of the {@link PayloadExchange}.
 *
 * @author Rob Winch
 * @since 5.2
 */
public class AuthorizationPayloadInterceptor implements PayloadInterceptor, Ordered {

	private final ReactiveAuthorizationManager<PayloadExchange> authorizationManager;

	private int order;

	public AuthorizationPayloadInterceptor(ReactiveAuthorizationManager<PayloadExchange> authorizationManager) {
		Assert.notNull(authorizationManager, "authorizationManager cannot be null");
		this.authorizationManager = authorizationManager;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public Mono<Void> intercept(PayloadExchange exchange, PayloadInterceptorChain chain) {
		return ReactiveSecurityContextHolder.getContext().filter((c) -> c.getAuthentication() != null)
				.map(SecurityContext::getAuthentication)
				.switchIfEmpty(Mono.error(() -> new AuthenticationCredentialsNotFoundException(
						"An Authentication (possibly AnonymousAuthenticationToken) is required.")))
				.as((authentication) -> this.authorizationManager.verify(authentication, exchange))
				.then(chain.next(exchange));
	}

}
