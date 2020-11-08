package org.springframework.security.core.session;

import java.util.Date;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link SessionInformation}.
 *
 * @author Ben Alex
 */
public class SessionInformationTests {

	@Test
	public void testObject() throws Exception {
		Object principal = "Some principal object";
		String sessionId = "1234567890";
		Date currentDate = new Date();
		SessionInformation info = new SessionInformation(principal, sessionId, currentDate);
		assertThat(info.getPrincipal()).isEqualTo(principal);
		assertThat(info.getSessionId()).isEqualTo(sessionId);
		assertThat(info.getLastRequest()).isEqualTo(currentDate);
		Thread.sleep(10);
		info.refreshLastRequest();
		assertThat(info.getLastRequest().after(currentDate)).isTrue();
	}

}
