package org.springframework.security.access;

import org.junit.Test;

import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.util.SimpleMethodInvocation;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link AuthorizedEvent}.
 *
 * @author Ben Alex
 */
public class AuthorizedEventTests {

	@Test
	public void testRejectsNulls() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AuthorizedEvent(null,
				SecurityConfig.createList("TEST"), new UsernamePasswordAuthenticationToken("foo", "bar")));
	}

	@Test
	public void testRejectsNulls2() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AuthorizedEvent(new SimpleMethodInvocation(), null,
				new UsernamePasswordAuthenticationToken("foo", "bar")));
	}

	@Test
	public void testRejectsNulls3() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new AuthorizedEvent(new SimpleMethodInvocation(), SecurityConfig.createList("TEST"), null));
	}

}
