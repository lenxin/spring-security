package org.springframework.security.web.authentication.preauth;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import static org.assertj.core.api.Assertions.assertThat;

public class Http403ForbiddenEntryPointTests {

	public void testCommence() throws IOException {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();
		Http403ForbiddenEntryPoint fep = new Http403ForbiddenEntryPoint();
		fep.commence(req, resp, new AuthenticationCredentialsNotFoundException("test"));
		assertThat(resp.getStatus()).withFailMessage("Incorrect status").isEqualTo(HttpServletResponse.SC_FORBIDDEN);
	}

}
