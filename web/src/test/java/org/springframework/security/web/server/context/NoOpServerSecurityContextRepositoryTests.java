package org.springframework.security.web.server.context;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class NoOpServerSecurityContextRepositoryTests {

	NoOpServerSecurityContextRepository repository = NoOpServerSecurityContextRepository.getInstance();

	ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());

	@Test
	public void saveAndLoad() {
		SecurityContext context = new SecurityContextImpl();
		Mono<SecurityContext> result = this.repository.save(this.exchange, context)
				.then(this.repository.load(this.exchange));
		StepVerifier.create(result).verifyComplete();
	}

}
