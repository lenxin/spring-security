package org.springframework.security.authentication;

import org.junit.Test;

import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests
 * {@link org.springframework.security.authentication.AuthenticationTrustResolverImpl}.
 *
 * @author Ben Alex
 */
public class AuthenticationTrustResolverImplTests {

	@Test
	public void testCorrectOperationIsAnonymous() {
		AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();
		assertThat(trustResolver.isAnonymous(
				new AnonymousAuthenticationToken("ignored", "ignored", AuthorityUtils.createAuthorityList("ignored"))))
						.isTrue();
		assertThat(trustResolver.isAnonymous(
				new TestingAuthenticationToken("ignored", "ignored", AuthorityUtils.createAuthorityList("ignored"))))
						.isFalse();
	}

	@Test
	public void testCorrectOperationIsRememberMe() {
		AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();
		assertThat(trustResolver.isRememberMe(
				new RememberMeAuthenticationToken("ignored", "ignored", AuthorityUtils.createAuthorityList("ignored"))))
						.isTrue();
		assertThat(trustResolver.isAnonymous(
				new TestingAuthenticationToken("ignored", "ignored", AuthorityUtils.createAuthorityList("ignored"))))
						.isFalse();
	}

	@Test
	public void testGettersSetters() {
		AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();
		assertThat(AnonymousAuthenticationToken.class).isEqualTo(trustResolver.getAnonymousClass());
		trustResolver.setAnonymousClass(TestingAuthenticationToken.class);
		assertThat(trustResolver.getAnonymousClass()).isEqualTo(TestingAuthenticationToken.class);
		assertThat(RememberMeAuthenticationToken.class).isEqualTo(trustResolver.getRememberMeClass());
		trustResolver.setRememberMeClass(TestingAuthenticationToken.class);
		assertThat(trustResolver.getRememberMeClass()).isEqualTo(TestingAuthenticationToken.class);
	}

}
