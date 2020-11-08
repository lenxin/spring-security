package org.springframework.security.core.context;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link SecurityContextHolder}.
 *
 * @author Ben Alex
 */
public class SecurityContextHolderTests {

	@Before
	public final void setUp() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Test
	public void testContextHolderGetterSetterClearer() {
		SecurityContext sc = new SecurityContextImpl();
		sc.setAuthentication(new UsernamePasswordAuthenticationToken("Foobar", "pass"));
		SecurityContextHolder.setContext(sc);
		assertThat(SecurityContextHolder.getContext()).isEqualTo(sc);
		SecurityContextHolder.clearContext();
		assertThat(SecurityContextHolder.getContext()).isNotSameAs(sc);
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testNeverReturnsNull() {
		assertThat(SecurityContextHolder.getContext()).isNotNull();
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testRejectsNulls() {
		assertThatIllegalArgumentException().isThrownBy(() -> SecurityContextHolder.setContext(null));
	}

}
