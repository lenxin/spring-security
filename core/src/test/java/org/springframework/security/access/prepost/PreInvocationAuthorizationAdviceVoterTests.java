package org.springframework.security.access.prepost;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.security.access.intercept.aspectj.MethodInvocationAdapter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PreInvocationAuthorizationAdviceVoterTests {

	@Mock
	private PreInvocationAuthorizationAdvice authorizationAdvice;

	private PreInvocationAuthorizationAdviceVoter voter;

	@Before
	public void setUp() {
		this.voter = new PreInvocationAuthorizationAdviceVoter(this.authorizationAdvice);
	}

	@Test
	public void supportsMethodInvocation() {
		assertThat(this.voter.supports(MethodInvocation.class)).isTrue();
	}

	// SEC-2031
	@Test
	public void supportsProxyMethodInvocation() {
		assertThat(this.voter.supports(ProxyMethodInvocation.class)).isTrue();
	}

	@Test
	public void supportsMethodInvocationAdapter() {
		assertThat(this.voter.supports(MethodInvocationAdapter.class)).isTrue();
	}

}
