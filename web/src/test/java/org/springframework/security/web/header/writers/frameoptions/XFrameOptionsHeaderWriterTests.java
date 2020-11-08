package org.springframework.security.web.header.writers.frameoptions;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @author Ankur Pathak
 * @since 5.0
 */
public class XFrameOptionsHeaderWriterTests {

	private MockHttpServletRequest request = new MockHttpServletRequest();

	private MockHttpServletResponse response = new MockHttpServletResponse();

	private static final String XFRAME_OPTIONS_HEADER = "X-Frame-Options";

	@Test
	public void writeHeadersWhenWhiteList() {
		WhiteListedAllowFromStrategy whitelist = new WhiteListedAllowFromStrategy(Arrays.asList("example.com"));
		XFrameOptionsHeaderWriter writer = new XFrameOptionsHeaderWriter(whitelist);
		writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderValue(XFrameOptionsHeaderWriter.XFRAME_OPTIONS_HEADER)).isEqualTo("DENY");
	}

	@Test
	public void writeHeaderWhenNotPresent() {
		WhiteListedAllowFromStrategy whitelist = new WhiteListedAllowFromStrategy(
				Collections.singletonList("example.com"));
		XFrameOptionsHeaderWriter writer = new XFrameOptionsHeaderWriter(whitelist);
		String value = new String("value");
		this.response.setHeader(XFRAME_OPTIONS_HEADER, value);
		writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(XFRAME_OPTIONS_HEADER)).isSameAs(value);
	}

}
