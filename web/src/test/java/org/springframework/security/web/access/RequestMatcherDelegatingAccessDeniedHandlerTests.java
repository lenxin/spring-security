package org.springframework.security.web.access;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Josh Cummings
 */
public class RequestMatcherDelegatingAccessDeniedHandlerTests {

	private RequestMatcherDelegatingAccessDeniedHandler delegator;

	private LinkedHashMap<RequestMatcher, AccessDeniedHandler> deniedHandlers;

	private AccessDeniedHandler accessDeniedHandler;

	private HttpServletRequest request;

	@Before
	public void setup() {
		this.accessDeniedHandler = mock(AccessDeniedHandler.class);
		this.deniedHandlers = new LinkedHashMap<>();
		this.request = new MockHttpServletRequest();
	}

	@Test
	public void handleWhenNothingMatchesThenOnlyDefaultHandlerInvoked() throws Exception {
		AccessDeniedHandler handler = mock(AccessDeniedHandler.class);
		RequestMatcher matcher = mock(RequestMatcher.class);
		given(matcher.matches(this.request)).willReturn(false);
		this.deniedHandlers.put(matcher, handler);
		this.delegator = new RequestMatcherDelegatingAccessDeniedHandler(this.deniedHandlers, this.accessDeniedHandler);
		this.delegator.handle(this.request, null, null);
		verify(this.accessDeniedHandler).handle(this.request, null, null);
		verify(handler, never()).handle(this.request, null, null);
	}

	@Test
	public void handleWhenFirstMatchesThenOnlyFirstInvoked() throws Exception {
		AccessDeniedHandler firstHandler = mock(AccessDeniedHandler.class);
		RequestMatcher firstMatcher = mock(RequestMatcher.class);
		AccessDeniedHandler secondHandler = mock(AccessDeniedHandler.class);
		RequestMatcher secondMatcher = mock(RequestMatcher.class);
		given(firstMatcher.matches(this.request)).willReturn(true);
		this.deniedHandlers.put(firstMatcher, firstHandler);
		this.deniedHandlers.put(secondMatcher, secondHandler);
		this.delegator = new RequestMatcherDelegatingAccessDeniedHandler(this.deniedHandlers, this.accessDeniedHandler);
		this.delegator.handle(this.request, null, null);
		verify(firstHandler).handle(this.request, null, null);
		verify(secondHandler, never()).handle(this.request, null, null);
		verify(this.accessDeniedHandler, never()).handle(this.request, null, null);
		verify(secondMatcher, never()).matches(this.request);
	}

	@Test
	public void handleWhenSecondMatchesThenOnlySecondInvoked() throws Exception {
		AccessDeniedHandler firstHandler = mock(AccessDeniedHandler.class);
		RequestMatcher firstMatcher = mock(RequestMatcher.class);
		AccessDeniedHandler secondHandler = mock(AccessDeniedHandler.class);
		RequestMatcher secondMatcher = mock(RequestMatcher.class);
		given(firstMatcher.matches(this.request)).willReturn(false);
		given(secondMatcher.matches(this.request)).willReturn(true);
		this.deniedHandlers.put(firstMatcher, firstHandler);
		this.deniedHandlers.put(secondMatcher, secondHandler);
		this.delegator = new RequestMatcherDelegatingAccessDeniedHandler(this.deniedHandlers, this.accessDeniedHandler);
		this.delegator.handle(this.request, null, null);
		verify(secondHandler).handle(this.request, null, null);
		verify(firstHandler, never()).handle(this.request, null, null);
		verify(this.accessDeniedHandler, never()).handle(this.request, null, null);
	}

}
