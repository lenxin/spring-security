package org.springframework.security.messaging.handler.invocation.reactive;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;
import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.security.authentication.TestAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.messaging.handler.invocation.ResolvableMethod;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
public class AuthenticationPrincipalArgumentResolverTests {

	private AuthenticationPrincipalArgumentResolver resolver = new AuthenticationPrincipalArgumentResolver();

	@Test
	public void supportsParameterWhenAuthenticationPrincipalThenTrue() {
		assertThat(this.resolver.supportsParameter(arg0("authenticationPrincipalOnMonoUserDetails"))).isTrue();
	}

	@Test
	public void resolveArgumentWhenAuthenticationPrincipalAndEmptyContextThenNull() {
		Object result = this.resolver.resolveArgument(arg0("authenticationPrincipalOnMonoUserDetails"), null).block();
		assertThat(result).isNull();
	}

	@Test
	public void resolveArgumentWhenAuthenticationPrincipalThenFound() {
		Authentication authentication = TestAuthentication.authenticatedUser();
		// @formatter:off
		Mono<UserDetails> result = (Mono<UserDetails>) this.resolver
				.resolveArgument(arg0("authenticationPrincipalOnMonoUserDetails"), null)
				.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication))
				.block();
		// @formatter:on
		assertThat(result.block()).isEqualTo(authentication.getPrincipal());
	}

	@SuppressWarnings("unused")
	private void authenticationPrincipalOnMonoUserDetails(@AuthenticationPrincipal Mono<UserDetails> user) {
	}

	@Test
	public void supportsParameterWhenCurrentUserThenTrue() {
		assertThat(this.resolver.supportsParameter(arg0("currentUserOnMonoUserDetails"))).isTrue();
	}

	@Test
	public void resolveArgumentWhenMonoAndAuthenticationPrincipalThenFound() {
		Authentication authentication = TestAuthentication.authenticatedUser();
		// @formatter:off
		Mono<UserDetails> result = (Mono<UserDetails>) this.resolver
				.resolveArgument(arg0("currentUserOnMonoUserDetails"), null)
				.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication))
				.block();
		// @formatter:on
		assertThat(result.block()).isEqualTo(authentication.getPrincipal());
	}

	@SuppressWarnings("unused")
	private void currentUserOnMonoUserDetails(@CurrentUser Mono<UserDetails> user) {
	}

	@Test
	public void resolveArgumentWhenExpressionThenFound() {
		Authentication authentication = TestAuthentication.authenticatedUser();
		// @formatter:off
		Mono<String> result = (Mono<String>) this.resolver
				.resolveArgument(arg0("authenticationPrincipalExpression"), null)
				.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication))
				.block();
		// @formatter:on
		assertThat(result.block()).isEqualTo(authentication.getName());
	}

	@SuppressWarnings("unused")
	private void authenticationPrincipalExpression(
			@AuthenticationPrincipal(expression = "username") Mono<String> username) {
	}

	@Test
	public void supportsParameterWhenNotAnnotatedThenFalse() {
		assertThat(this.resolver.supportsParameter(arg0("monoUserDetails"))).isFalse();
	}

	@SuppressWarnings("unused")
	private void monoUserDetails(Mono<UserDetails> user) {
	}

	private MethodParameter arg0(String methodName) {
		ResolvableMethod method = ResolvableMethod.on(getClass()).named(methodName).method();
		return new SynthesizingMethodParameter(method.method(), 0);
	}

	@AuthenticationPrincipal
	@Retention(RetentionPolicy.RUNTIME)
	@interface CurrentUser {

	}

}
