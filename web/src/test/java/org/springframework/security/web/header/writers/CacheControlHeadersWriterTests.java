package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest(ReflectionUtils.class)
public class CacheControlHeadersWriterTests {

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private CacheControlHeadersWriter writer;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.writer = new CacheControlHeadersWriter();
	}

	@Test
	public void writeHeaders() {
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames().size()).isEqualTo(3);
		assertThat(this.response.getHeaderValues("Cache-Control"))
				.containsOnly("no-cache, no-store, max-age=0, must-revalidate");
		assertThat(this.response.getHeaderValues("Pragma")).containsOnly("no-cache");
		assertThat(this.response.getHeaderValues("Expires")).containsOnly("0");
	}

	// gh-2953
	@Test
	public void writeHeadersDisabledIfCacheControl() {
		this.response.setHeader("Cache-Control", "max-age: 123");
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("Cache-Control")).containsOnly("max-age: 123");
		assertThat(this.response.getHeaderValue("Pragma")).isNull();
		assertThat(this.response.getHeaderValue("Expires")).isNull();
	}

	@Test
	public void writeHeadersDisabledIfPragma() {
		this.response.setHeader("Pragma", "mock");
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("Pragma")).containsOnly("mock");
		assertThat(this.response.getHeaderValue("Expires")).isNull();
		assertThat(this.response.getHeaderValue("Cache-Control")).isNull();
	}

	@Test
	public void writeHeadersDisabledIfExpires() {
		this.response.setHeader("Expires", "mock");
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("Expires")).containsOnly("mock");
		assertThat(this.response.getHeaderValue("Cache-Control")).isNull();
		assertThat(this.response.getHeaderValue("Pragma")).isNull();
	}

	@Test
	// gh-5534
	public void writeHeadersDisabledIfNotModified() {
		this.response.setStatus(HttpStatus.NOT_MODIFIED.value());
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).isEmpty();
	}

}
