package org.springframework.security.access.intercept;

import org.junit.Test;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.util.SimpleMethodInvocation;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests some {@link AbstractSecurityInterceptor} methods. Most of the testing for this
 * class is found in the {@code MethodSecurityInterceptorTests} class.
 *
 * @author Ben Alex
 */
public class AbstractSecurityInterceptorTests {

	@Test
	public void detectsIfInvocationPassedIncompatibleSecureObject() {
		MockSecurityInterceptorWhichOnlySupportsStrings si = new MockSecurityInterceptorWhichOnlySupportsStrings();
		si.setRunAsManager(mock(RunAsManager.class));
		si.setAuthenticationManager(mock(AuthenticationManager.class));
		si.setAfterInvocationManager(mock(AfterInvocationManager.class));
		si.setAccessDecisionManager(mock(AccessDecisionManager.class));
		si.setSecurityMetadataSource(mock(SecurityMetadataSource.class));
		assertThatIllegalArgumentException().isThrownBy(() -> si.beforeInvocation(new SimpleMethodInvocation()));
	}

	@Test
	public void detectsViolationOfGetSecureObjectClassMethod() throws Exception {
		MockSecurityInterceptorReturnsNull si = new MockSecurityInterceptorReturnsNull();
		si.setRunAsManager(mock(RunAsManager.class));
		si.setAuthenticationManager(mock(AuthenticationManager.class));
		si.setAfterInvocationManager(mock(AfterInvocationManager.class));
		si.setAccessDecisionManager(mock(AccessDecisionManager.class));
		si.setSecurityMetadataSource(mock(SecurityMetadataSource.class));
		assertThatIllegalArgumentException().isThrownBy(si::afterPropertiesSet);
	}

	private class MockSecurityInterceptorReturnsNull extends AbstractSecurityInterceptor {

		private SecurityMetadataSource securityMetadataSource;

		@Override
		public Class<?> getSecureObjectClass() {
			return null;
		}

		@Override
		public SecurityMetadataSource obtainSecurityMetadataSource() {
			return this.securityMetadataSource;
		}

		void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
			this.securityMetadataSource = securityMetadataSource;
		}

	}

	private class MockSecurityInterceptorWhichOnlySupportsStrings extends AbstractSecurityInterceptor {

		private SecurityMetadataSource securityMetadataSource;

		@Override
		public Class<?> getSecureObjectClass() {
			return String.class;
		}

		@Override
		public SecurityMetadataSource obtainSecurityMetadataSource() {
			return this.securityMetadataSource;
		}

		void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
			this.securityMetadataSource = securityMetadataSource;
		}

	}

}
