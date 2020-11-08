package org.springframework.security.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HttpStatusRequestRejectedHandlerTests {

	@Test
	public void httpStatusRequestRejectedHandlerUsesStatus400byDefault() throws Exception {
		HttpStatusRequestRejectedHandler sut = new HttpStatusRequestRejectedHandler();
		HttpServletResponse response = mock(HttpServletResponse.class);
		sut.handle(mock(HttpServletRequest.class), response, mock(RequestRejectedException.class));
		verify(response).sendError(400);
	}

	@Test
	public void httpStatusRequestRejectedHandlerCanBeConfiguredToUseStatus() throws Exception {
		httpStatusRequestRejectedHandlerCanBeConfiguredToUseStatusHelper(400);
		httpStatusRequestRejectedHandlerCanBeConfiguredToUseStatusHelper(403);
		httpStatusRequestRejectedHandlerCanBeConfiguredToUseStatusHelper(500);
	}

	private void httpStatusRequestRejectedHandlerCanBeConfiguredToUseStatusHelper(int status) throws Exception {
		HttpStatusRequestRejectedHandler sut = new HttpStatusRequestRejectedHandler(status);
		HttpServletResponse response = mock(HttpServletResponse.class);
		sut.handle(mock(HttpServletRequest.class), response, mock(RequestRejectedException.class));
		verify(response).sendError(status);
	}

}
