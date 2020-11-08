package org.springframework.security.authentication.rcp;

import java.util.Collection;

import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link RemoteAuthenticationProvider}.
 *
 * @author Ben Alex
 */
public class RemoteAuthenticationProviderTests {

	@Test
	public void testExceptionsGetPassedBackToCaller() {
		RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
		provider.setRemoteAuthenticationManager(new MockRemoteAuthenticationManager(false));
		assertThatExceptionOfType(RemoteAuthenticationException.class)
				.isThrownBy(() -> provider.authenticate(new UsernamePasswordAuthenticationToken("rod", "password")));
	}

	@Test
	public void testGettersSetters() {
		RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
		provider.setRemoteAuthenticationManager(new MockRemoteAuthenticationManager(true));
		assertThat(provider.getRemoteAuthenticationManager()).isNotNull();
	}

	@Test
	public void testStartupChecksAuthenticationManagerSet() throws Exception {
		RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
		assertThatIllegalArgumentException().isThrownBy(provider::afterPropertiesSet);
		provider.setRemoteAuthenticationManager(new MockRemoteAuthenticationManager(true));
		provider.afterPropertiesSet();
	}

	@Test
	public void testSuccessfulAuthenticationCreatesObject() {
		RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
		provider.setRemoteAuthenticationManager(new MockRemoteAuthenticationManager(true));
		Authentication result = provider.authenticate(new UsernamePasswordAuthenticationToken("rod", "password"));
		assertThat(result.getPrincipal()).isEqualTo("rod");
		assertThat(result.getCredentials()).isEqualTo("password");
		assertThat(AuthorityUtils.authorityListToSet(result.getAuthorities())).contains("foo");
	}

	@Test
	public void testNullCredentialsDoesNotCauseNullPointerException() {
		RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
		provider.setRemoteAuthenticationManager(new MockRemoteAuthenticationManager(false));
		assertThatExceptionOfType(RemoteAuthenticationException.class)
				.isThrownBy(() -> provider.authenticate(new UsernamePasswordAuthenticationToken("rod", null)));
	}

	@Test
	public void testSupports() {
		RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
		assertThat(provider.supports(UsernamePasswordAuthenticationToken.class)).isTrue();
	}

	private class MockRemoteAuthenticationManager implements RemoteAuthenticationManager {

		private boolean grantAccess;

		MockRemoteAuthenticationManager(boolean grantAccess) {
			this.grantAccess = grantAccess;
		}

		@Override
		public Collection<? extends GrantedAuthority> attemptAuthentication(String username, String password)
				throws RemoteAuthenticationException {
			if (this.grantAccess) {
				return AuthorityUtils.createAuthorityList("foo");
			}
			else {
				throw new RemoteAuthenticationException("as requested");
			}
		}

	}

}
