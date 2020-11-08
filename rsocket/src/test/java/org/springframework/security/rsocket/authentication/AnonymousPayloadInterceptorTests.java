package org.springframework.security.rsocket.authentication;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.rsocket.api.PayloadExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 */
@RunWith(MockitoJUnitRunner.class)
public class AnonymousPayloadInterceptorTests {

	@Mock
	private PayloadExchange exchange;

	private AnonymousPayloadInterceptor interceptor;

	@Before
	public void setup() {
		this.interceptor = new AnonymousPayloadInterceptor("key");
	}

	@Test
	public void constructorKeyWhenKeyNullThenException() {
		String key = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousPayloadInterceptor(key));
	}

	@Test
	public void constructorKeyPrincipalAuthoritiesWhenKeyNullThenException() {
		String key = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousPayloadInterceptor(key, "principal",
				AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
	}

	@Test
	public void constructorKeyPrincipalAuthoritiesWhenPrincipalNullThenException() {
		Object principal = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousPayloadInterceptor("key", principal,
				AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
	}

	@Test
	public void constructorKeyPrincipalAuthoritiesWhenAuthoritiesNullThenException() {
		List<GrantedAuthority> authorities = null;
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AnonymousPayloadInterceptor("key", "principal", authorities));
	}

	@Test
	public void interceptWhenNoAuthenticationThenAnonymousAuthentication() {
		AuthenticationPayloadInterceptorChain chain = new AuthenticationPayloadInterceptorChain();
		this.interceptor.intercept(this.exchange, chain).block();
		Authentication authentication = chain.getAuthentication();
		assertThat(authentication).isInstanceOf(AnonymousAuthenticationToken.class);
	}

	@Test
	public void interceptWhenAuthenticationThenOriginalAuthentication() {
		AuthenticationPayloadInterceptorChain chain = new AuthenticationPayloadInterceptorChain();
		TestingAuthenticationToken expected = new TestingAuthenticationToken("test", "password");
		this.interceptor.intercept(this.exchange, chain)
				.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(expected)).block();
		Authentication authentication = chain.getAuthentication();
		assertThat(authentication).isEqualTo(expected);
	}

}
