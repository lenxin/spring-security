package org.springframework.security.authentication.event;

import org.junit.Test;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link AbstractAuthenticationEvent} and its subclasses.
 *
 * @author Ben Alex
 */
public class AuthenticationEventTests {

	private Authentication getAuthentication() {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("Principal",
				"Credentials");
		authentication.setDetails("127.0.0.1");
		return authentication;
	}

	@Test
	public void testAbstractAuthenticationEvent() {
		Authentication auth = getAuthentication();
		AbstractAuthenticationEvent event = new AuthenticationSuccessEvent(auth);
		assertThat(event.getAuthentication()).isEqualTo(auth);
	}

	@Test
	public void testAbstractAuthenticationFailureEvent() {
		Authentication auth = getAuthentication();
		AuthenticationException exception = new DisabledException("TEST");
		AbstractAuthenticationFailureEvent event = new AuthenticationFailureDisabledEvent(auth, exception);
		assertThat(event.getAuthentication()).isEqualTo(auth);
		assertThat(event.getException()).isEqualTo(exception);
	}

	@Test
	public void testRejectsNullAuthentication() {
		AuthenticationException exception = new DisabledException("TEST");
		assertThatIllegalArgumentException().isThrownBy(() -> new AuthenticationFailureDisabledEvent(null, exception));
	}

	@Test
	public void testRejectsNullAuthenticationException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AuthenticationFailureDisabledEvent(getAuthentication(), null));
	}

}
