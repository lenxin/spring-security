package org.springframework.security.rsocket.authentication;

import reactor.core.publisher.Mono;

import org.springframework.core.Ordered;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.api.PayloadInterceptor;
import org.springframework.security.rsocket.api.PayloadInterceptorChain;
import org.springframework.util.Assert;

/**
 * Uses the provided {@code ReactiveAuthenticationManager} to authenticate a Payload. If
 * authentication is successful, then the result is added to
 * {@link ReactiveSecurityContextHolder}.
 *
 * @author Rob Winch
 * @since 5.2
 */
public class AuthenticationPayloadInterceptor implements PayloadInterceptor, Ordered {

	private final ReactiveAuthenticationManager authenticationManager;

	private int order;

	private PayloadExchangeAuthenticationConverter authenticationConverter = new BasicAuthenticationPayloadExchangeConverter();

	/**
	 * Creates a new instance
	 * @param authenticationManager the manager to use. Cannot be null
	 */
	public AuthenticationPayloadInterceptor(ReactiveAuthenticationManager authenticationManager) {
		Assert.notNull(authenticationManager, "authenticationManager cannot be null");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Sets the convert to be used
	 * @param authenticationConverter
	 */
	public void setAuthenticationConverter(PayloadExchangeAuthenticationConverter authenticationConverter) {
		Assert.notNull(authenticationConverter, "authenticationConverter cannot be null");
		this.authenticationConverter = authenticationConverter;
	}

	@Override
	public Mono<Void> intercept(PayloadExchange exchange, PayloadInterceptorChain chain) {
		return this.authenticationConverter.convert(exchange).switchIfEmpty(chain.next(exchange).then(Mono.empty()))
				.flatMap((a) -> this.authenticationManager.authenticate(a))
				.flatMap((a) -> onAuthenticationSuccess(chain.next(exchange), a));
	}

	private Mono<Void> onAuthenticationSuccess(Mono<Void> payload, Authentication authentication) {
		return payload.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
	}

}
