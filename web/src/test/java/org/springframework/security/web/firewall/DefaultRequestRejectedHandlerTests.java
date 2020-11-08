package org.springframework.security.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

public class DefaultRequestRejectedHandlerTests {

	@Test
	public void defaultRequestRejectedHandlerRethrowsTheException() throws Exception {
		RequestRejectedException requestRejectedException = new RequestRejectedException("rejected");
		DefaultRequestRejectedHandler sut = new DefaultRequestRejectedHandler();
		assertThatExceptionOfType(RequestRejectedException.class).isThrownBy(() -> sut
				.handle(mock(HttpServletRequest.class), mock(HttpServletResponse.class), requestRejectedException))
				.withMessage("rejected");
	}

}
