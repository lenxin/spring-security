package org.springframework.security.web.authentication;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SavedRequestAwareAuthenticationSuccessHandlerTests {

	@Test
	public void defaultUrlMuststartWithSlashOrHttpScheme() {
		SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setDefaultTargetUrl("/acceptableRelativeUrl");
		handler.setDefaultTargetUrl("https://some.site.org/index.html");
		handler.setDefaultTargetUrl("https://some.site.org/index.html");
		assertThatIllegalArgumentException().isThrownBy(() -> handler.setDefaultTargetUrl("missingSlash"));
	}

	@Test
	public void onAuthenticationSuccessHasSavedRequest() throws Exception {
		String redirectUrl = "http://localhost/appcontext/page";
		RedirectStrategy redirectStrategy = mock(RedirectStrategy.class);
		RequestCache requestCache = mock(RequestCache.class);
		SavedRequest savedRequest = mock(SavedRequest.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		given(savedRequest.getRedirectUrl()).willReturn(redirectUrl);
		given(requestCache.getRequest(request, response)).willReturn(savedRequest);
		SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setRequestCache(requestCache);
		handler.setRedirectStrategy(redirectStrategy);
		handler.onAuthenticationSuccess(request, response, mock(Authentication.class));
		verify(redirectStrategy).sendRedirect(request, response, redirectUrl);
	}

}
