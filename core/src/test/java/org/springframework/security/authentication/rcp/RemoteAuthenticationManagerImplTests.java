package org.springframework.security.authentication.rcp;

import org.junit.Test;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link RemoteAuthenticationManagerImpl}.
 *
 * @author Ben Alex
 */
public class RemoteAuthenticationManagerImplTests {

	@Test
	public void testFailedAuthenticationReturnsRemoteAuthenticationException() {
		RemoteAuthenticationManagerImpl manager = new RemoteAuthenticationManagerImpl();
		AuthenticationManager am = mock(AuthenticationManager.class);
		given(am.authenticate(any(Authentication.class))).willThrow(new BadCredentialsException(""));
		manager.setAuthenticationManager(am);
		assertThatExceptionOfType(RemoteAuthenticationException.class)
				.isThrownBy(() -> manager.attemptAuthentication("rod", "password"));
	}

	@Test
	public void testStartupChecksAuthenticationManagerSet() throws Exception {
		RemoteAuthenticationManagerImpl manager = new RemoteAuthenticationManagerImpl();
		assertThatIllegalArgumentException().isThrownBy(manager::afterPropertiesSet);
		manager.setAuthenticationManager(mock(AuthenticationManager.class));
		manager.afterPropertiesSet();
	}

	@Test
	public void testSuccessfulAuthentication() {
		RemoteAuthenticationManagerImpl manager = new RemoteAuthenticationManagerImpl();
		AuthenticationManager am = mock(AuthenticationManager.class);
		given(am.authenticate(any(Authentication.class))).willReturn(new TestingAuthenticationToken("u", "p", "A"));
		manager.setAuthenticationManager(am);
		manager.attemptAuthentication("rod", "password");
	}

}
