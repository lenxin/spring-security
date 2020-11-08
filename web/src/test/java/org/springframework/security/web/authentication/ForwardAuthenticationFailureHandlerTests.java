package org.springframework.security.web.authentication;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * <p>
 * Forward Authentication Failure Handler Tests
 * </p>
 *
 * @author Shazin Sadakath @since4.1
 */
public class ForwardAuthenticationFailureHandlerTests {

	@Test
	public void invalidForwardUrl() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ForwardAuthenticationFailureHandler("aaa"));
	}

	@Test
	public void emptyForwardUrl() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ForwardAuthenticationFailureHandler(""));
	}

	@Test
	public void responseIsForwarded() throws Exception {
		ForwardAuthenticationFailureHandler fafh = new ForwardAuthenticationFailureHandler("/forwardUrl");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		AuthenticationException e = mock(AuthenticationException.class);
		fafh.onAuthenticationFailure(request, response, e);
		assertThat(response.getForwardedUrl()).isEqualTo("/forwardUrl");
		assertThat(request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).isEqualTo(e);
	}

}
