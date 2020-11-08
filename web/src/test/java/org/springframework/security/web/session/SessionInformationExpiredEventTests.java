package org.springframework.security.web.session;

import java.util.Date;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.session.SessionInformation;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 * @since 4.2
 */
public class SessionInformationExpiredEventTests {

	@Test
	public void constructorWhenSessionInformationNullThenThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SessionInformationExpiredEvent(null,
				new MockHttpServletRequest(), new MockHttpServletResponse()));
	}

	@Test
	public void constructorWhenRequestNullThenThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SessionInformationExpiredEvent(
				new SessionInformation("fake", "sessionId", new Date()), null, new MockHttpServletResponse()));
	}

	@Test
	public void constructorWhenResponseNullThenThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SessionInformationExpiredEvent(
				new SessionInformation("fake", "sessionId", new Date()), new MockHttpServletRequest(), null));
	}

}
