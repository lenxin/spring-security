package org.springframework.security.web.authentication.logout;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * DelegatingLogoutSuccessHandlerTests Tests
 *
 * @author Shazin Sadakath
 * @author Rob Winch
 */
@RunWith(MockitoJUnitRunner.class)
public class DelegatingLogoutSuccessHandlerTests {

	@Mock
	RequestMatcher matcher;

	@Mock
	RequestMatcher matcher2;

	@Mock
	LogoutSuccessHandler handler;

	@Mock
	LogoutSuccessHandler handler2;

	@Mock
	LogoutSuccessHandler defaultHandler;

	@Mock
	HttpServletRequest request;

	@Mock
	MockHttpServletResponse response;

	@Mock
	Authentication authentication;

	DelegatingLogoutSuccessHandler delegatingHandler;

	@Before
	public void setup() {
		LinkedHashMap<RequestMatcher, LogoutSuccessHandler> matcherToHandler = new LinkedHashMap<>();
		matcherToHandler.put(this.matcher, this.handler);
		matcherToHandler.put(this.matcher2, this.handler2);
		this.delegatingHandler = new DelegatingLogoutSuccessHandler(matcherToHandler);
	}

	@Test
	public void onLogoutSuccessFirstMatches() throws Exception {
		this.delegatingHandler.setDefaultLogoutSuccessHandler(this.defaultHandler);
		given(this.matcher.matches(this.request)).willReturn(true);
		this.delegatingHandler.onLogoutSuccess(this.request, this.response, this.authentication);
		verify(this.handler).onLogoutSuccess(this.request, this.response, this.authentication);
		verifyZeroInteractions(this.matcher2, this.handler2, this.defaultHandler);
	}

	@Test
	public void onLogoutSuccessSecondMatches() throws Exception {
		this.delegatingHandler.setDefaultLogoutSuccessHandler(this.defaultHandler);
		given(this.matcher2.matches(this.request)).willReturn(true);
		this.delegatingHandler.onLogoutSuccess(this.request, this.response, this.authentication);
		verify(this.handler2).onLogoutSuccess(this.request, this.response, this.authentication);
		verifyZeroInteractions(this.handler, this.defaultHandler);
	}

	@Test
	public void onLogoutSuccessDefault() throws Exception {
		this.delegatingHandler.setDefaultLogoutSuccessHandler(this.defaultHandler);
		this.delegatingHandler.onLogoutSuccess(this.request, this.response, this.authentication);
		verify(this.defaultHandler).onLogoutSuccess(this.request, this.response, this.authentication);
		verifyZeroInteractions(this.handler, this.handler2);
	}

	@Test
	public void onLogoutSuccessNoMatchDefaultNull() throws Exception {
		this.delegatingHandler.onLogoutSuccess(this.request, this.response, this.authentication);
		verifyZeroInteractions(this.handler, this.handler2, this.defaultHandler);
	}

}
