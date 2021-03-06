package org.springframework.security.web.header.writers.frameoptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class FrameOptionsHeaderWriterTests {

	@Mock
	private AllowFromStrategy strategy;

	private MockHttpServletResponse response;

	private MockHttpServletRequest request;

	private XFrameOptionsHeaderWriter writer;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}

	@Test
	public void constructorNullMode() {
		assertThatIllegalArgumentException().isThrownBy(() -> new XFrameOptionsHeaderWriter((XFrameOptionsMode) null));
	}

	@Test
	public void constructorAllowFromNoAllowFromStrategy() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new XFrameOptionsHeaderWriter(XFrameOptionsMode.ALLOW_FROM));
	}

	@Test
	public void constructorNullAllowFromStrategy() {
		assertThatIllegalArgumentException().isThrownBy(() -> new XFrameOptionsHeaderWriter((AllowFromStrategy) null));
	}

	@Test
	public void writeHeadersAllowFromReturnsNull() {
		this.writer = new XFrameOptionsHeaderWriter(this.strategy);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames().isEmpty()).isTrue();
	}

	@Test
	public void writeHeadersAllowFrom() {
		String allowFromValue = "https://example.com/";
		given(this.strategy.getAllowFromValue(this.request)).willReturn(allowFromValue);
		this.writer = new XFrameOptionsHeaderWriter(this.strategy);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader(XFrameOptionsHeaderWriter.XFRAME_OPTIONS_HEADER))
				.isEqualTo("ALLOW-FROM " + allowFromValue);
	}

	@Test
	public void writeHeadersDeny() {
		this.writer = new XFrameOptionsHeaderWriter(XFrameOptionsMode.DENY);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader(XFrameOptionsHeaderWriter.XFRAME_OPTIONS_HEADER)).isEqualTo("DENY");
	}

	@Test
	public void writeHeadersSameOrigin() {
		this.writer = new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader(XFrameOptionsHeaderWriter.XFRAME_OPTIONS_HEADER)).isEqualTo("SAMEORIGIN");
	}

	@Test
	public void writeHeadersTwiceLastWins() {
		this.writer = new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN);
		this.writer.writeHeaders(this.request, this.response);
		this.writer = new XFrameOptionsHeaderWriter(XFrameOptionsMode.DENY);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader(XFrameOptionsHeaderWriter.XFRAME_OPTIONS_HEADER)).isEqualTo("DENY");
	}

}
