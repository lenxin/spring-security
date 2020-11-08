package org.springframework.security.web.server.savedrequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ServerRequestCacheWebFilter}
 *
 * @author Eleftheria Stein
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerRequestCacheWebFilterTests {

	private ServerRequestCacheWebFilter requestCacheFilter;

	@Mock
	private WebFilterChain chain;

	@Mock
	private ServerRequestCache requestCache;

	@Captor
	private ArgumentCaptor<ServerWebExchange> exchangeCaptor;

	@Before
	public void setup() {
		this.requestCacheFilter = new ServerRequestCacheWebFilter();
		this.requestCacheFilter.setRequestCache(this.requestCache);
		given(this.chain.filter(any(ServerWebExchange.class))).willReturn(Mono.empty());
	}

	@Test
	public void filterWhenRequestMatchesThenRequestUpdated() {
		ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/"));
		ServerHttpRequest savedRequest = MockServerHttpRequest.get("/")
				.header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML.getType()).build();
		given(this.requestCache.removeMatchingRequest(any())).willReturn(Mono.just(savedRequest));
		this.requestCacheFilter.filter(exchange, this.chain).block();
		verify(this.chain).filter(this.exchangeCaptor.capture());
		ServerWebExchange updatedExchange = this.exchangeCaptor.getValue();
		assertThat(updatedExchange.getRequest()).isEqualTo(savedRequest);
	}

	@Test
	public void filterWhenRequestDoesNotMatchThenRequestDoesNotChange() {
		MockServerHttpRequest initialRequest = MockServerHttpRequest.get("/").build();
		ServerWebExchange exchange = MockServerWebExchange.from(initialRequest);
		given(this.requestCache.removeMatchingRequest(any())).willReturn(Mono.empty());
		this.requestCacheFilter.filter(exchange, this.chain).block();
		verify(this.chain).filter(this.exchangeCaptor.capture());
		ServerWebExchange updatedExchange = this.exchangeCaptor.getValue();
		assertThat(updatedExchange.getRequest()).isEqualTo(initialRequest);
	}

}
