package org.springframework.security.web.server.transport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link HttpsRedirectWebFilter}
 *
 * @author Josh Cummings
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpsRedirectWebFilterTests {

	HttpsRedirectWebFilter filter;

	@Mock
	WebFilterChain chain;

	@Before
	public void configureFilter() {
		this.filter = new HttpsRedirectWebFilter();
		given(this.chain.filter(any(ServerWebExchange.class))).willReturn(Mono.empty());
	}

	@Test
	public void filterWhenExchangeIsInsecureThenRedirects() {
		ServerWebExchange exchange = get("http://localhost");
		this.filter.filter(exchange, this.chain).block();
		assertThat(statusCode(exchange)).isEqualTo(302);
		assertThat(redirectedUrl(exchange)).isEqualTo("https://localhost");
	}

	@Test
	public void filterWhenExchangeIsSecureThenNoRedirect() {
		ServerWebExchange exchange = get("https://localhost");
		this.filter.filter(exchange, this.chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isNull();
	}

	@Test
	public void filterWhenExchangeMismatchesThenNoRedirect() {
		ServerWebExchangeMatcher matcher = mock(ServerWebExchangeMatcher.class);
		given(matcher.matches(any(ServerWebExchange.class)))
				.willReturn(ServerWebExchangeMatcher.MatchResult.notMatch());
		this.filter.setRequiresHttpsRedirectMatcher(matcher);
		ServerWebExchange exchange = get("http://localhost:8080");
		this.filter.filter(exchange, this.chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isNull();
	}

	@Test
	public void filterWhenExchangeMatchesAndRequestIsInsecureThenRedirects() {
		ServerWebExchangeMatcher matcher = mock(ServerWebExchangeMatcher.class);
		given(matcher.matches(any(ServerWebExchange.class))).willReturn(ServerWebExchangeMatcher.MatchResult.match());
		this.filter.setRequiresHttpsRedirectMatcher(matcher);
		ServerWebExchange exchange = get("http://localhost:8080");
		this.filter.filter(exchange, this.chain).block();
		assertThat(statusCode(exchange)).isEqualTo(302);
		assertThat(redirectedUrl(exchange)).isEqualTo("https://localhost:8443");
		verify(matcher).matches(any(ServerWebExchange.class));
	}

	@Test
	public void filterWhenRequestIsInsecureThenPortMapperRemapsPort() {
		PortMapper portMapper = mock(PortMapper.class);
		given(portMapper.lookupHttpsPort(314)).willReturn(159);
		this.filter.setPortMapper(portMapper);
		ServerWebExchange exchange = get("http://localhost:314");
		this.filter.filter(exchange, this.chain).block();
		assertThat(statusCode(exchange)).isEqualTo(302);
		assertThat(redirectedUrl(exchange)).isEqualTo("https://localhost:159");
		verify(portMapper).lookupHttpsPort(314);
	}

	@Test
	public void filterWhenRequestIsInsecureAndNoPortMappingThenThrowsIllegalState() {
		ServerWebExchange exchange = get("http://localhost:1234");
		assertThatIllegalStateException().isThrownBy(() -> this.filter.filter(exchange, this.chain).block());
	}

	@Test
	public void filterWhenInsecureRequestHasAPathThenRedirects() {
		ServerWebExchange exchange = get("http://localhost:8080/path/page.html?query=string");
		this.filter.filter(exchange, this.chain).block();
		assertThat(statusCode(exchange)).isEqualTo(302);
		assertThat(redirectedUrl(exchange)).isEqualTo("https://localhost:8443/path/page.html?query=string");
	}

	@Test
	public void setRequiresTransportSecurityMatcherWhenSetWithNullValueThenThrowsIllegalArgument() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.filter.setRequiresHttpsRedirectMatcher(null));
	}

	@Test
	public void setPortMapperWhenSetWithNullValueThenThrowsIllegalArgument() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.filter.setPortMapper(null));
	}

	private String redirectedUrl(ServerWebExchange exchange) {
		return exchange.getResponse().getHeaders().get(HttpHeaders.LOCATION).iterator().next();
	}

	private int statusCode(ServerWebExchange exchange) {
		return exchange.getResponse().getStatusCode().value();
	}

	private ServerWebExchange get(String uri) {
		return MockServerWebExchange.from(MockServerHttpRequest.get(uri).build());
	}

}
