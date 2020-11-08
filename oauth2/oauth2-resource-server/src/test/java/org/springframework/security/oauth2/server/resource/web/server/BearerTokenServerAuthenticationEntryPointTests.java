package org.springframework.security.oauth2.server.resource.web.server;

import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.resource.BearerTokenError;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.1
 */
public class BearerTokenServerAuthenticationEntryPointTests {

	private BearerTokenServerAuthenticationEntryPoint entryPoint = new BearerTokenServerAuthenticationEntryPoint();

	private MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/"));

	@Test
	public void commenceWhenNotOAuth2AuthenticationExceptionThenBearer() {
		this.entryPoint.commence(this.exchange, new BadCredentialsException("")).block();
		assertThat(getResponse().getHeaders().getFirst(HttpHeaders.WWW_AUTHENTICATE)).isEqualTo("Bearer");
		assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void commenceWhenRealmNameThenHasRealmName() {
		this.entryPoint.setRealmName("Realm");
		this.entryPoint.commence(this.exchange, new BadCredentialsException("")).block();
		assertThat(getResponse().getHeaders().getFirst(HttpHeaders.WWW_AUTHENTICATE))
				.isEqualTo("Bearer realm=\"Realm\"");
		assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void commenceWhenOAuth2AuthenticationExceptionThenContainsErrorInformation() {
		OAuth2Error oauthError = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
		OAuth2AuthenticationException exception = new OAuth2AuthenticationException(oauthError);
		this.entryPoint.commence(this.exchange, exception).block();
		assertThat(getResponse().getHeaders().getFirst(HttpHeaders.WWW_AUTHENTICATE))
				.isEqualTo("Bearer error=\"invalid_request\"");
		assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void commenceWhenOAuth2ErrorCompleteThenContainsErrorInformation() {
		OAuth2Error oauthError = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "Oops", "https://example.com");
		OAuth2AuthenticationException exception = new OAuth2AuthenticationException(oauthError);
		this.entryPoint.commence(this.exchange, exception).block();
		assertThat(getResponse().getHeaders().getFirst(HttpHeaders.WWW_AUTHENTICATE)).isEqualTo(
				"Bearer error=\"invalid_request\", error_description=\"Oops\", error_uri=\"https://example.com\"");
		assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void commenceWhenBearerTokenThenErrorInformation() {
		OAuth2Error oauthError = new BearerTokenError(OAuth2ErrorCodes.INVALID_REQUEST, HttpStatus.BAD_REQUEST, "Oops",
				"https://example.com");
		OAuth2AuthenticationException exception = new OAuth2AuthenticationException(oauthError);
		this.entryPoint.commence(this.exchange, exception).block();
		assertThat(getResponse().getHeaders().getFirst(HttpHeaders.WWW_AUTHENTICATE)).isEqualTo(
				"Bearer error=\"invalid_request\", error_description=\"Oops\", error_uri=\"https://example.com\"");
		assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void commenceWhenNoSubscriberThenNothingHappens() {
		this.entryPoint.commence(this.exchange, new BadCredentialsException(""));
		assertThat(getResponse().getHeaders()).isEmpty();
		assertThat(getResponse().getStatusCode()).isNull();
	}

	private MockServerHttpResponse getResponse() {
		return this.exchange.getResponse();
	}

}
