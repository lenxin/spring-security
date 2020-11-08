package org.springframework.security.web.authentication;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * <p>
 * Forward Authentication Failure Handler Tests
 * </p>
 *
 * @author Shazin Sadakath
 * @since 4.1
 */
public class ForwardAuthenticaionSuccessHandlerTests {

	@Test
	public void invalidForwardUrl() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ForwardAuthenticationSuccessHandler("aaa"));
	}

	@Test
	public void emptyForwardUrl() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ForwardAuthenticationSuccessHandler(""));
	}

	@Test
	public void responseIsForwarded() throws Exception {
		ForwardAuthenticationSuccessHandler fash = new ForwardAuthenticationSuccessHandler("/forwardUrl");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Authentication authentication = mock(Authentication.class);
		fash.onAuthenticationSuccess(request, response, authentication);
		assertThat(response.getForwardedUrl()).isEqualTo("/forwardUrl");
	}

}
