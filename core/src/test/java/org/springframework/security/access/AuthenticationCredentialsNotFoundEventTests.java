package org.springframework.security.access;

import org.junit.Test;

import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.util.SimpleMethodInvocation;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link AuthenticationCredentialsNotFoundEvent}.
 *
 * @author Ben Alex
 */
public class AuthenticationCredentialsNotFoundEventTests {

	@Test
	public void testRejectsNulls() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AuthenticationCredentialsNotFoundEvent(null,
				SecurityConfig.createList("TEST"), new AuthenticationCredentialsNotFoundException("test")));
	}

	@Test
	public void testRejectsNulls2() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AuthenticationCredentialsNotFoundEvent(new SimpleMethodInvocation(), null,
						new AuthenticationCredentialsNotFoundException("test")));
	}

	@Test
	public void testRejectsNulls3() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AuthenticationCredentialsNotFoundEvent(new SimpleMethodInvocation(),
						SecurityConfig.createList("TEST"), null));
	}

}
