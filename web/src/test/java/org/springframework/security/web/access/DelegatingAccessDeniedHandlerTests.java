package org.springframework.security.web.access;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DelegatingAccessDeniedHandlerTests {

	@Mock
	private AccessDeniedHandler handler1;

	@Mock
	private AccessDeniedHandler handler2;

	@Mock
	private AccessDeniedHandler handler3;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	private LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers;

	private DelegatingAccessDeniedHandler handler;

	@Before
	public void setup() {
		this.handlers = new LinkedHashMap<>();
	}

	@Test
	public void moreSpecificDoesNotInvokeLessSpecific() throws Exception {
		this.handlers.put(CsrfException.class, this.handler1);
		this.handler = new DelegatingAccessDeniedHandler(this.handlers, this.handler3);
		AccessDeniedException accessDeniedException = new AccessDeniedException("");
		this.handler.handle(this.request, this.response, accessDeniedException);
		verify(this.handler1, never()).handle(any(HttpServletRequest.class), any(HttpServletResponse.class),
				any(AccessDeniedException.class));
		verify(this.handler3).handle(this.request, this.response, accessDeniedException);
	}

	@Test
	public void matchesDoesNotInvokeDefault() throws Exception {
		this.handlers.put(InvalidCsrfTokenException.class, this.handler1);
		this.handlers.put(MissingCsrfTokenException.class, this.handler2);
		this.handler = new DelegatingAccessDeniedHandler(this.handlers, this.handler3);
		AccessDeniedException accessDeniedException = new MissingCsrfTokenException("123");
		this.handler.handle(this.request, this.response, accessDeniedException);
		verify(this.handler1, never()).handle(any(HttpServletRequest.class), any(HttpServletResponse.class),
				any(AccessDeniedException.class));
		verify(this.handler2).handle(this.request, this.response, accessDeniedException);
		verify(this.handler3, never()).handle(any(HttpServletRequest.class), any(HttpServletResponse.class),
				any(AccessDeniedException.class));
	}

}
