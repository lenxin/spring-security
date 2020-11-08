package org.springframework.security.authentication.event;

import org.junit.Test;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * Tests {@link LoggerListener}.
 *
 * @author Ben Alex
 */
public class LoggerListenerTests {

	private Authentication getAuthentication() {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("Principal",
				"Credentials");
		authentication.setDetails("127.0.0.1");
		return authentication;
	}

	@Test
	public void testLogsEvents() {
		AuthenticationFailureDisabledEvent event = new AuthenticationFailureDisabledEvent(getAuthentication(),
				new LockedException("TEST"));
		LoggerListener listener = new LoggerListener();
		listener.onApplicationEvent(event);
	}

}
