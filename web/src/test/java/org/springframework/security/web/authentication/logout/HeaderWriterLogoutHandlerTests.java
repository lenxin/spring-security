package org.springframework.security.web.authentication.logout;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.header.HeaderWriter;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rafiullah Hamedy
 * @author Josh Cummings
 * @see HeaderWriterLogoutHandler
 */
public class HeaderWriterLogoutHandlerTests {

	private MockHttpServletResponse response;

	private MockHttpServletRequest request;

	@Before
	public void setup() {
		this.response = new MockHttpServletResponse();
		this.request = new MockHttpServletRequest();
	}

	@Test
	public void constructorWhenHeaderWriterIsNullThenThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new HeaderWriterLogoutHandler(null))
				.withMessage("headerWriter cannot be null");
	}

	@Test
	public void logoutWhenHasHeaderWriterThenInvoked() {
		HeaderWriter headerWriter = mock(HeaderWriter.class);
		HeaderWriterLogoutHandler handler = new HeaderWriterLogoutHandler(headerWriter);
		handler.logout(this.request, this.response, mock(Authentication.class));
		verify(headerWriter).writeHeaders(this.request, this.response);
	}

}
