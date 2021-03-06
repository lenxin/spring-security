package org.springframework.security.web.server.authorization;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ExceptionTranslationWebFilterTests {

	@Mock
	private Principal principal;

	@Mock
	private ServerWebExchange exchange;

	@Mock
	private WebFilterChain chain;

	@Mock
	private ServerAccessDeniedHandler deniedHandler;

	@Mock
	private ServerAuthenticationEntryPoint entryPoint;

	private PublisherProbe<Void> deniedPublisher = PublisherProbe.empty();

	private PublisherProbe<Void> entryPointPublisher = PublisherProbe.empty();

	private ExceptionTranslationWebFilter filter = new ExceptionTranslationWebFilter();

	@Before
	public void setup() {
		given(this.exchange.getResponse()).willReturn(new MockServerHttpResponse());
		given(this.deniedHandler.handle(any(), any())).willReturn(this.deniedPublisher.mono());
		given(this.entryPoint.commence(any(), any())).willReturn(this.entryPointPublisher.mono());
		this.filter.setAuthenticationEntryPoint(this.entryPoint);
		this.filter.setAccessDeniedHandler(this.deniedHandler);
	}

	@Test
	public void filterWhenNoExceptionThenNotHandled() {
		given(this.chain.filter(this.exchange)).willReturn(Mono.empty());
		StepVerifier.create(this.filter.filter(this.exchange, this.chain)).expectComplete().verify();
		this.deniedPublisher.assertWasNotSubscribed();
		this.entryPointPublisher.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenNotAccessDeniedExceptionThenNotHandled() {
		given(this.chain.filter(this.exchange)).willReturn(Mono.error(new IllegalArgumentException("oops")));
		StepVerifier.create(this.filter.filter(this.exchange, this.chain)).expectError(IllegalArgumentException.class)
				.verify();
		this.deniedPublisher.assertWasNotSubscribed();
		this.entryPointPublisher.assertWasNotSubscribed();
	}

	@Test
	public void filterWhenAccessDeniedExceptionAndNotAuthenticatedThenHandled() {
		given(this.exchange.getPrincipal()).willReturn(Mono.empty());
		given(this.chain.filter(this.exchange)).willReturn(Mono.error(new AccessDeniedException("Not Authorized")));
		StepVerifier.create(this.filter.filter(this.exchange, this.chain)).verifyComplete();
		this.deniedPublisher.assertWasNotSubscribed();
		this.entryPointPublisher.assertWasSubscribed();
	}

	@Test
	public void filterWhenDefaultsAndAccessDeniedExceptionAndAuthenticatedThenForbidden() {
		this.filter = new ExceptionTranslationWebFilter();
		given(this.exchange.getPrincipal()).willReturn(Mono.just(this.principal));
		given(this.chain.filter(this.exchange)).willReturn(Mono.error(new AccessDeniedException("Not Authorized")));
		StepVerifier.create(this.filter.filter(this.exchange, this.chain)).expectComplete().verify();
		assertThat(this.exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void filterWhenDefaultsAndAccessDeniedExceptionAndNotAuthenticatedThenUnauthorized() {
		this.filter = new ExceptionTranslationWebFilter();
		given(this.exchange.getPrincipal()).willReturn(Mono.empty());
		given(this.chain.filter(this.exchange)).willReturn(Mono.error(new AccessDeniedException("Not Authorized")));
		StepVerifier.create(this.filter.filter(this.exchange, this.chain)).expectComplete().verify();
		assertThat(this.exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void filterWhenAccessDeniedExceptionAndAuthenticatedThenHandled() {
		given(this.exchange.getPrincipal()).willReturn(Mono.just(this.principal));
		given(this.chain.filter(this.exchange)).willReturn(Mono.error(new AccessDeniedException("Not Authorized")));
		StepVerifier.create(this.filter.filter(this.exchange, this.chain)).expectComplete().verify();
		this.deniedPublisher.assertWasSubscribed();
		this.entryPointPublisher.assertWasNotSubscribed();
	}

	@Test
	public void setAccessDeniedHandlerWhenNullThenException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.filter.setAccessDeniedHandler(null));
	}

	@Test
	public void setAuthenticationEntryPointWhenNullThenException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.filter.setAuthenticationEntryPoint(null));
	}

}
