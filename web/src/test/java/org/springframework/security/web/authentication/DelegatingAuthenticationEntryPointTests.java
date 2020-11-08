package org.springframework.security.web.authentication;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link DelegatingAuthenticationEntryPoint}
 *
 * @author Mike Wiesner
 * @since 3.0.2
 * @version $Id:$
 */
public class DelegatingAuthenticationEntryPointTests {

	private DelegatingAuthenticationEntryPoint daep;

	private LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints;

	private AuthenticationEntryPoint defaultEntryPoint;

	private HttpServletRequest request = new MockHttpServletRequest();

	@Before
	public void before() {
		this.defaultEntryPoint = mock(AuthenticationEntryPoint.class);
		this.entryPoints = new LinkedHashMap<>();
		this.daep = new DelegatingAuthenticationEntryPoint(this.entryPoints);
		this.daep.setDefaultEntryPoint(this.defaultEntryPoint);
	}

	@Test
	public void testDefaultEntryPoint() throws Exception {
		AuthenticationEntryPoint firstAEP = mock(AuthenticationEntryPoint.class);
		RequestMatcher firstRM = mock(RequestMatcher.class);
		given(firstRM.matches(this.request)).willReturn(false);
		this.entryPoints.put(firstRM, firstAEP);
		this.daep.commence(this.request, null, null);
		verify(this.defaultEntryPoint).commence(this.request, null, null);
		verify(firstAEP, never()).commence(this.request, null, null);
	}

	@Test
	public void testFirstEntryPoint() throws Exception {
		AuthenticationEntryPoint firstAEP = mock(AuthenticationEntryPoint.class);
		RequestMatcher firstRM = mock(RequestMatcher.class);
		AuthenticationEntryPoint secondAEP = mock(AuthenticationEntryPoint.class);
		RequestMatcher secondRM = mock(RequestMatcher.class);
		given(firstRM.matches(this.request)).willReturn(true);
		this.entryPoints.put(firstRM, firstAEP);
		this.entryPoints.put(secondRM, secondAEP);
		this.daep.commence(this.request, null, null);
		verify(firstAEP).commence(this.request, null, null);
		verify(secondAEP, never()).commence(this.request, null, null);
		verify(this.defaultEntryPoint, never()).commence(this.request, null, null);
		verify(secondRM, never()).matches(this.request);
	}

	@Test
	public void testSecondEntryPoint() throws Exception {
		AuthenticationEntryPoint firstAEP = mock(AuthenticationEntryPoint.class);
		RequestMatcher firstRM = mock(RequestMatcher.class);
		AuthenticationEntryPoint secondAEP = mock(AuthenticationEntryPoint.class);
		RequestMatcher secondRM = mock(RequestMatcher.class);
		given(firstRM.matches(this.request)).willReturn(false);
		given(secondRM.matches(this.request)).willReturn(true);
		this.entryPoints.put(firstRM, firstAEP);
		this.entryPoints.put(secondRM, secondAEP);
		this.daep.commence(this.request, null, null);
		verify(secondAEP).commence(this.request, null, null);
		verify(firstAEP, never()).commence(this.request, null, null);
		verify(this.defaultEntryPoint, never()).commence(this.request, null, null);
	}

}
