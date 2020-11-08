package org.springframework.security.rsocket.authentication;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.api.PayloadInterceptorChain;

/**
 * @author Rob Winch
 */
class AuthenticationPayloadInterceptorChain implements PayloadInterceptorChain {

	private Authentication authentication;

	@Override
	public Mono<Void> next(PayloadExchange exchange) {
		return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
				.doOnNext((a) -> this.setAuthentication(a)).then();
	}

	Authentication getAuthentication() {
		return this.authentication;
	}

	void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
