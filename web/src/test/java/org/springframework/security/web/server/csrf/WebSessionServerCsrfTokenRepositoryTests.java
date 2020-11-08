package org.springframework.security.web.server.csrf;

import java.util.Map;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebSession;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class WebSessionServerCsrfTokenRepositoryTests {

	private WebSessionServerCsrfTokenRepository repository = new WebSessionServerCsrfTokenRepository();

	private MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/"));

	@Test
	public void generateTokenThenNoSession() {
		Mono<CsrfToken> result = this.repository.generateToken(this.exchange);
		Mono<Boolean> isSessionStarted = this.exchange.getSession().map(WebSession::isStarted);
		StepVerifier.create(isSessionStarted).expectNext(false).verifyComplete();
	}

	@Test
	public void generateTokenWhenSubscriptionThenNoSession() {
		Mono<CsrfToken> result = this.repository.generateToken(this.exchange);
		Mono<Boolean> isSessionStarted = this.exchange.getSession().map(WebSession::isStarted);
		StepVerifier.create(isSessionStarted).expectNext(false).verifyComplete();
	}

	@Test
	public void saveTokenWhenDefaultThenAddsToSession() {
		Mono<CsrfToken> result = this.repository.generateToken(this.exchange)
				.delayUntil((t) -> this.repository.saveToken(this.exchange, t));
		result.block();
		WebSession session = this.exchange.getSession().block();
		Map<String, Object> attributes = session.getAttributes();
		assertThat(session.isStarted()).isTrue();
		assertThat(attributes).hasSize(1);
		assertThat(attributes.values().iterator().next()).isInstanceOf(CsrfToken.class);
	}

	@Test
	public void saveTokenWhenNullThenDeletes() {
		CsrfToken token = this.repository.generateToken(this.exchange).block();
		Mono<Void> result = this.repository.saveToken(this.exchange, null);
		StepVerifier.create(result).verifyComplete();
		WebSession session = this.exchange.getSession().block();
		assertThat(session.getAttributes()).isEmpty();
	}

	@Test
	public void saveTokenChangeSessionId() {
		String originalSessionId = this.exchange.getSession().block().getId();
		this.repository.saveToken(this.exchange, null).block();
		WebSession session = this.exchange.getSession().block();
		assertThat(session.getId()).isNotEqualTo(originalSessionId);
	}

}
