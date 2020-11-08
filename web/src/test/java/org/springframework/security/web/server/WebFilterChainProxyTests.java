package org.springframework.security.web.server;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class WebFilterChainProxyTests {

	// gh-4668
	@Test
	public void filterWhenNoMatchThenContinuesChainAnd404() {
		List<WebFilter> filters = Arrays.asList(new Http200WebFilter());
		ServerWebExchangeMatcher notMatch = (exchange) -> MatchResult.notMatch();
		MatcherSecurityWebFilterChain chain = new MatcherSecurityWebFilterChain(notMatch, filters);
		WebFilterChainProxy filter = new WebFilterChainProxy(chain);
		WebTestClient.bindToController(new Object()).webFilter(filter).build().get().exchange().expectStatus()
				.isNotFound();
	}

	static class Http200WebFilter implements WebFilter {

		@Override
		public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
			return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
		}

	}

}
