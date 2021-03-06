package org.springframework.security.web.server.savedrequest;

import java.net.URI;

import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class WebSessionServerRequestCacheTests {

	private WebSessionServerRequestCache cache = new WebSessionServerRequestCache();

	@Test
	public void saveRequestGetRequestWhenGetThenFound() {
		MockServerWebExchange exchange = MockServerWebExchange
				.from(MockServerHttpRequest.get("/secured/").accept(MediaType.TEXT_HTML));
		this.cache.saveRequest(exchange).block();
		URI saved = this.cache.getRedirectUri(exchange).block();
		assertThat(saved).isEqualTo(exchange.getRequest().getURI());
	}

	@Test
	public void saveRequestGetRequestWithQueryParamsWhenGetThenFound() {
		MockServerWebExchange exchange = MockServerWebExchange
				.from(MockServerHttpRequest.get("/secured/").queryParam("key", "value").accept(MediaType.TEXT_HTML));
		this.cache.saveRequest(exchange).block();
		URI saved = this.cache.getRedirectUri(exchange).block();
		assertThat(saved).isEqualTo(exchange.getRequest().getURI());
	}

	@Test
	public void saveRequestGetRequestWhenFaviconThenNotFound() {
		MockServerWebExchange exchange = MockServerWebExchange
				.from(MockServerHttpRequest.get("/favicon.png").accept(MediaType.TEXT_HTML));
		this.cache.saveRequest(exchange).block();
		URI saved = this.cache.getRedirectUri(exchange).block();
		assertThat(saved).isNull();
	}

	@Test
	public void saveRequestGetRequestWhenPostThenNotFound() {
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.post("/secured/"));
		this.cache.saveRequest(exchange).block();
		assertThat(this.cache.getRedirectUri(exchange).block()).isNull();
	}

	@Test
	public void saveRequestGetRequestWhenPostAndCustomMatcherThenFound() {
		this.cache.setSaveRequestMatcher((e) -> ServerWebExchangeMatcher.MatchResult.match());
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.post("/secured/"));
		this.cache.saveRequest(exchange).block();
		URI saved = this.cache.getRedirectUri(exchange).block();
		assertThat(saved).isEqualTo(exchange.getRequest().getURI());
	}

	@Test
	public void saveRequestRemoveRequestWhenThenFound() {
		MockServerWebExchange exchange = MockServerWebExchange
				.from(MockServerHttpRequest.get("/secured/").accept(MediaType.TEXT_HTML));
		this.cache.saveRequest(exchange).block();
		ServerHttpRequest saved = this.cache.removeMatchingRequest(exchange).block();
		assertThat(saved.getURI()).isEqualTo(exchange.getRequest().getURI());
	}

	@Test
	public void removeRequestGetRequestWhenDefaultThenNotFound() {
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/secured/"));
		this.cache.saveRequest(exchange).block();
		this.cache.removeMatchingRequest(exchange).block();
		assertThat(this.cache.getRedirectUri(exchange).block()).isNull();
	}

}
