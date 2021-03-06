package org.springframework.security.web.savedrequest;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.MockPortResolver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DefaultSavedRequestTests {

	// SEC-308, SEC-315
	@Test
	public void headersAreCaseInsensitive() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("USER-aGenT", "Mozilla");
		DefaultSavedRequest saved = new DefaultSavedRequest(request, new MockPortResolver(8080, 8443));
		assertThat(saved.getHeaderValues("user-agent").get(0)).isEqualTo("Mozilla");
	}

	// SEC-1412
	@Test
	public void discardsIfNoneMatchHeader() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("If-None-Match", "somehashvalue");
		DefaultSavedRequest saved = new DefaultSavedRequest(request, new MockPortResolver(8080, 8443));
		assertThat(saved.getHeaderValues("if-none-match").isEmpty()).isTrue();
	}

	// SEC-3082
	@Test
	public void parametersAreCaseSensitive() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("AnotHerTest", "Hi dad");
		request.addParameter("thisisatest", "Hi mom");
		DefaultSavedRequest saved = new DefaultSavedRequest(request, new MockPortResolver(8080, 8443));
		assertThat(saved.getParameterValues("thisisatest")[0]).isEqualTo("Hi mom");
		assertThat(saved.getParameterValues("anothertest")).isNull();
	}

}
