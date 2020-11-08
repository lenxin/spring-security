package org.springframework.security.web.server.authentication.logout;

import org.junit.Test;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author MD Sayem Ahmed
 * @since 5.2
 */
public class HeaderWriterServerLogoutHandlerTests {

	@Test
	public void constructorWhenHeadersWriterIsNullThenExceptionThrown() {
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new HeaderWriterServerLogoutHandler(null));
	}

	@Test
	public void logoutWhenInvokedThenWritesResponseHeaders() {
		ServerHttpHeadersWriter headersWriter = mock(ServerHttpHeadersWriter.class);
		HeaderWriterServerLogoutHandler handler = new HeaderWriterServerLogoutHandler(headersWriter);
		ServerWebExchange serverWebExchange = mock(ServerWebExchange.class);
		WebFilterExchange filterExchange = mock(WebFilterExchange.class);
		given(filterExchange.getExchange()).willReturn(serverWebExchange);
		Authentication authentication = mock(Authentication.class);
		handler.logout(filterExchange, authentication);
		verify(headersWriter).writeHttpHeaders(serverWebExchange);
	}

}
