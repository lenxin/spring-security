package org.springframework.security.web.csrf;

import org.junit.Test;

/**
 * @author Rob Winch
 *
 */
public class MissingCsrfTokenExceptionTests {

	// CsrfChannelInterceptor requires this to work
	@Test
	public void nullExpectedTokenDoesNotFail() {
		new MissingCsrfTokenException(null);
	}

}
