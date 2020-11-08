package org.springframework.security.web;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Luke Taylor
 * @since 3.0
 */
public class DefaultRedirectStrategyTests {

	@Test
	public void contextRelativeUrlWithContextNameInHostnameIsHandledCorrectly() throws Exception {
		DefaultRedirectStrategy rds = new DefaultRedirectStrategy();
		rds.setContextRelative(true);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/context");
		MockHttpServletResponse response = new MockHttpServletResponse();
		rds.sendRedirect(request, response, "https://context.blah.com/context/remainder");
		assertThat(response.getRedirectedUrl()).isEqualTo("remainder");
	}

	// SEC-2177
	@Test
	public void contextRelativeUrlWithMultipleSchemesInHostnameIsHandledCorrectly() throws Exception {
		DefaultRedirectStrategy rds = new DefaultRedirectStrategy();
		rds.setContextRelative(true);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/context");
		MockHttpServletResponse response = new MockHttpServletResponse();
		rds.sendRedirect(request, response, "https://https://context.blah.com/context/remainder");
		assertThat(response.getRedirectedUrl()).isEqualTo("remainder");
	}

	@Test
	public void contextRelativeShouldThrowExceptionIfURLDoesNotContainContextPath() throws Exception {
		DefaultRedirectStrategy rds = new DefaultRedirectStrategy();
		rds.setContextRelative(true);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/context");
		MockHttpServletResponse response = new MockHttpServletResponse();
		assertThatIllegalArgumentException()
				.isThrownBy(() -> rds.sendRedirect(request, response, "https://redirectme.somewhere.else"));
	}

}
