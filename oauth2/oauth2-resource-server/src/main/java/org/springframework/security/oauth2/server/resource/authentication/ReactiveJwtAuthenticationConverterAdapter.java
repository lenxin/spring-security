package org.springframework.security.oauth2.server.resource.authentication;

import reactor.core.publisher.Mono;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

/**
 * A reactive {@link Converter} for adapting a non-blocking imperative {@link Converter}
 *
 * @author Josh Cummings
 * @since 5.1.1
 */
public class ReactiveJwtAuthenticationConverterAdapter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

	private final Converter<Jwt, AbstractAuthenticationToken> delegate;

	public ReactiveJwtAuthenticationConverterAdapter(Converter<Jwt, AbstractAuthenticationToken> delegate) {
		Assert.notNull(delegate, "delegate cannot be null");
		this.delegate = delegate;
	}

	@Override
	public final Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
		return Mono.just(jwt).map(this.delegate::convert);
	}

}
