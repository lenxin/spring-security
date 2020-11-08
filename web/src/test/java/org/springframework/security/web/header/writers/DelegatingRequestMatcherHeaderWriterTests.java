package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DelegatingRequestMatcherHeaderWriterTests {

	@Mock
	private RequestMatcher matcher;

	@Mock
	private HeaderWriter delegate;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private DelegatingRequestMatcherHeaderWriter headerWriter;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.headerWriter = new DelegatingRequestMatcherHeaderWriter(this.matcher, this.delegate);
	}

	@Test
	public void constructorNullRequestMatcher() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingRequestMatcherHeaderWriter(null, this.delegate));
	}

	@Test
	public void constructorNullDelegate() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DelegatingRequestMatcherHeaderWriter(this.matcher, null));
	}

	@Test
	public void writeHeadersOnMatch() {
		given(this.matcher.matches(this.request)).willReturn(true);
		this.headerWriter.writeHeaders(this.request, this.response);
		verify(this.delegate).writeHeaders(this.request, this.response);
	}

	@Test
	public void writeHeadersOnNoMatch() {
		given(this.matcher.matches(this.request)).willReturn(false);
		this.headerWriter.writeHeaders(this.request, this.response);
		verify(this.delegate, times(0)).writeHeaders(this.request, this.response);
	}

}
