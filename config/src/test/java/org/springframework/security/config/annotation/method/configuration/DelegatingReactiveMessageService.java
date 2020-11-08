package org.springframework.security.config.annotation.method.configuration;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public class DelegatingReactiveMessageService implements ReactiveMessageService {

	private final ReactiveMessageService delegate;

	public DelegatingReactiveMessageService(ReactiveMessageService delegate) {
		this.delegate = delegate;
	}

	@Override
	@PreAuthorize("denyAll")
	public String notPublisherPreAuthorizeFindById(long id) {
		return this.delegate.notPublisherPreAuthorizeFindById(id);
	}

	@Override
	public Mono<String> monoFindById(long id) {
		return this.delegate.monoFindById(id);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<String> monoPreAuthorizeHasRoleFindById(long id) {
		return this.delegate.monoPreAuthorizeHasRoleFindById(id);
	}

	@Override
	@PostAuthorize("returnObject?.contains(authentication?.name)")
	public Mono<String> monoPostAuthorizeFindById(long id) {
		return this.delegate.monoPostAuthorizeFindById(id);
	}

	@Override
	@PreAuthorize("@authz.check(#id)")
	public Mono<String> monoPreAuthorizeBeanFindById(long id) {
		return this.delegate.monoPreAuthorizeBeanFindById(id);
	}

	@Override
	@PostAuthorize("@authz.check(authentication, returnObject)")
	public Mono<String> monoPostAuthorizeBeanFindById(long id) {
		return this.delegate.monoPostAuthorizeBeanFindById(id);
	}

	@Override
	public Flux<String> fluxFindById(long id) {
		return this.delegate.fluxFindById(id);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Flux<String> fluxPreAuthorizeHasRoleFindById(long id) {
		return this.delegate.fluxPreAuthorizeHasRoleFindById(id);
	}

	@Override
	@PostAuthorize("returnObject?.contains(authentication?.name)")
	public Flux<String> fluxPostAuthorizeFindById(long id) {
		return this.delegate.fluxPostAuthorizeFindById(id);
	}

	@Override
	@PreAuthorize("@authz.check(#id)")
	public Flux<String> fluxPreAuthorizeBeanFindById(long id) {
		return this.delegate.fluxPreAuthorizeBeanFindById(id);
	}

	@Override
	@PostAuthorize("@authz.check(authentication, returnObject)")
	public Flux<String> fluxPostAuthorizeBeanFindById(long id) {
		return this.delegate.fluxPostAuthorizeBeanFindById(id);
	}

	@Override
	public Publisher<String> publisherFindById(long id) {
		return this.delegate.publisherFindById(id);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Publisher<String> publisherPreAuthorizeHasRoleFindById(long id) {
		return this.delegate.publisherPreAuthorizeHasRoleFindById(id);
	}

	@Override
	@PostAuthorize("returnObject?.contains(authentication?.name)")
	public Publisher<String> publisherPostAuthorizeFindById(long id) {
		return this.delegate.publisherPostAuthorizeFindById(id);
	}

	@Override
	@PreAuthorize("@authz.check(#id)")
	public Publisher<String> publisherPreAuthorizeBeanFindById(long id) {
		return this.delegate.publisherPreAuthorizeBeanFindById(id);
	}

	@Override
	@PostAuthorize("@authz.check(authentication, returnObject)")
	public Publisher<String> publisherPostAuthorizeBeanFindById(long id) {
		return this.delegate.publisherPostAuthorizeBeanFindById(id);
	}

}
