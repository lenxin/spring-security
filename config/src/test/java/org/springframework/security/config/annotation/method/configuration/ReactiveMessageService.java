package org.springframework.security.config.annotation.method.configuration;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveMessageService {

	String notPublisherPreAuthorizeFindById(long id);

	Mono<String> monoFindById(long id);

	Mono<String> monoPreAuthorizeHasRoleFindById(long id);

	Mono<String> monoPostAuthorizeFindById(long id);

	Mono<String> monoPreAuthorizeBeanFindById(long id);

	Mono<String> monoPostAuthorizeBeanFindById(long id);

	Flux<String> fluxFindById(long id);

	Flux<String> fluxPreAuthorizeHasRoleFindById(long id);

	Flux<String> fluxPostAuthorizeFindById(long id);

	Flux<String> fluxPreAuthorizeBeanFindById(long id);

	Flux<String> fluxPostAuthorizeBeanFindById(long id);

	Publisher<String> publisherFindById(long id);

	Publisher<String> publisherPreAuthorizeHasRoleFindById(long id);

	Publisher<String> publisherPostAuthorizeFindById(long id);

	Publisher<String> publisherPreAuthorizeBeanFindById(long id);

	Publisher<String> publisherPostAuthorizeBeanFindById(long id);

}
