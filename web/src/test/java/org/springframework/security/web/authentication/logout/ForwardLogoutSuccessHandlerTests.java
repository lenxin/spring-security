package org.springframework.security.web.authentication.logout;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ForwardLogoutSuccessHandler}.
 *
 * @author Vedran Pavic
 */
public class ForwardLogoutSuccessHandlerTests {

	@Test
	public void invalidTargetUrl() {
		String targetUrl = "not.valid";
		assertThatIllegalArgumentException().isThrownBy(() -> new ForwardLogoutSuccessHandler(targetUrl))
				.withMessage("'" + targetUrl + "' is not a valid target URL");
	}

	@Test
	public void emptyTargetUrl() {
		String targetUrl = " ";
		assertThatIllegalArgumentException().isThrownBy(() -> new ForwardLogoutSuccessHandler(targetUrl))
				.withMessage("'" + targetUrl + "' is not a valid target URL");
	}

	@Test
	public void logoutSuccessIsHandled() throws Exception {
		String targetUrl = "/login?logout";
		ForwardLogoutSuccessHandler handler = new ForwardLogoutSuccessHandler(targetUrl);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Authentication authentication = mock(Authentication.class);
		handler.onLogoutSuccess(request, response, authentication);
		assertThat(response.getForwardedUrl()).isEqualTo(targetUrl);
	}

}
