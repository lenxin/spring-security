package org.springframework.security.web.firewall;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Luke Taylor
 */
public class RequestWrapperTests {

	private static Map<String, String> testPaths = new LinkedHashMap<>();

	@BeforeClass
	// Some of these may be unrealistic values, but we can't be sure because of the
	// inconsistency in the spec.
	public static void createTestMap() {
		testPaths.put("/path1;x=y;z=w/path2;x=y/path3;x=y", "/path1/path2/path3");
		testPaths.put("/path1;x=y/path2;x=y/", "/path1/path2/");
		testPaths.put("/path1//path2/", "/path1/path2/");
		testPaths.put("//path1/path2//", "/path1/path2/");
		testPaths.put(";x=y;z=w", "");
	}

	@Test
	public void pathParametersAreRemovedFromServletPath() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		for (Map.Entry<String, String> entry : testPaths.entrySet()) {
			String path = entry.getKey();
			String expectedResult = entry.getValue();
			request.setServletPath(path);
			RequestWrapper wrapper = new RequestWrapper(request);
			assertThat(wrapper.getServletPath()).isEqualTo(expectedResult);
			wrapper.reset();
			assertThat(wrapper.getServletPath()).isEqualTo(path);
		}
	}

	@Test
	public void pathParametersAreRemovedFromPathInfo() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		for (Map.Entry<String, String> entry : testPaths.entrySet()) {
			String path = entry.getKey();
			String expectedResult = entry.getValue();
			// Should be null when stripped value is empty
			if (expectedResult.length() == 0) {
				expectedResult = null;
			}
			request.setPathInfo(path);
			RequestWrapper wrapper = new RequestWrapper(request);
			assertThat(wrapper.getPathInfo()).isEqualTo(expectedResult);
			wrapper.reset();
			assertThat(wrapper.getPathInfo()).isEqualTo(path);
		}
	}

	@Test
	public void resetWhenForward() throws Exception {
		String denormalizedPath = testPaths.keySet().iterator().next();
		String forwardPath = "/forward/path";
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpServletResponse mockResponse = mock(HttpServletResponse.class);
		RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
		given(mockRequest.getServletPath()).willReturn("");
		given(mockRequest.getPathInfo()).willReturn(denormalizedPath);
		given(mockRequest.getRequestDispatcher(forwardPath)).willReturn(mockDispatcher);
		RequestWrapper wrapper = new RequestWrapper(mockRequest);
		RequestDispatcher dispatcher = wrapper.getRequestDispatcher(forwardPath);
		dispatcher.forward(mockRequest, mockResponse);
		verify(mockRequest).getRequestDispatcher(forwardPath);
		verify(mockDispatcher).forward(mockRequest, mockResponse);
		assertThat(wrapper.getPathInfo()).isEqualTo(denormalizedPath);
		verify(mockRequest, times(2)).getPathInfo();
		// validate wrapper.getServletPath() delegates to the mock
		wrapper.getServletPath();
		verify(mockRequest, times(2)).getServletPath();
		verifyNoMoreInteractions(mockRequest, mockResponse, mockDispatcher);
	}

	@Test
	public void requestDispatcherNotWrappedAfterReset() {
		String path = "/forward/path";
		HttpServletRequest request = mock(HttpServletRequest.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		given(request.getRequestDispatcher(path)).willReturn(dispatcher);
		RequestWrapper wrapper = new RequestWrapper(request);
		wrapper.reset();
		assertThat(wrapper.getRequestDispatcher(path)).isSameAs(dispatcher);
	}

}
