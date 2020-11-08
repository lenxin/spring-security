package org.springframework.security.web.savedrequest;

import java.util.Base64;

import javax.servlet.http.Cookie;

import org.junit.Test;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestCacheAwareFilterTests {

	@Test
	public void doFilterWhenHttpSessionRequestCacheConfiguredThenSavedRequestRemovedAfterMatch() throws Exception {
		RequestCacheAwareFilter filter = new RequestCacheAwareFilter();
		HttpSessionRequestCache cache = new HttpSessionRequestCache();
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/destination");
		MockHttpServletResponse response = new MockHttpServletResponse();
		cache.saveRequest(request, response);
		assertThat(request.getSession().getAttribute(HttpSessionRequestCache.SAVED_REQUEST)).isNotNull();
		filter.doFilter(request, response, new MockFilterChain());
		assertThat(request.getSession().getAttribute(HttpSessionRequestCache.SAVED_REQUEST)).isNull();
	}

	@Test
	public void doFilterWhenCookieRequestCacheConfiguredThenExpiredSavedRequestCookieSetAfterMatch() throws Exception {
		CookieRequestCache cache = new CookieRequestCache();
		RequestCacheAwareFilter filter = new RequestCacheAwareFilter(cache);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("abc.com");
		request.setRequestURI("/destination");
		request.setScheme("https");
		request.setServerPort(443);
		request.setSecure(true);
		String encodedRedirectUrl = Base64.getEncoder().encodeToString("https://abc.com/destination".getBytes());
		Cookie savedRequest = new Cookie("REDIRECT_URI", encodedRedirectUrl);
		savedRequest.setMaxAge(-1);
		savedRequest.setSecure(request.isSecure());
		savedRequest.setPath("/");
		savedRequest.setHttpOnly(true);
		request.setCookies(savedRequest);
		MockHttpServletResponse response = new MockHttpServletResponse();
		filter.doFilter(request, response, new MockFilterChain());
		Cookie expiredCookie = response.getCookie("REDIRECT_URI");
		assertThat(expiredCookie).isNotNull();
		assertThat(expiredCookie.getValue()).isEmpty();
		assertThat(expiredCookie.getMaxAge()).isZero();
	}

}
