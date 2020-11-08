package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Rafiullah Hamedy
 * @author Josh Cummings
 * @see ClearSiteDataHeaderWriter
 */
public class ClearSiteDataHeaderWriterTests {

	private static final String HEADER_NAME = "Clear-Site-Data";

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.request.setSecure(true);
		this.response = new MockHttpServletResponse();
	}

	@Test
	public void createInstanceWhenMissingSourceThenThrowsException() {
		assertThatExceptionOfType(Exception.class).isThrownBy(() -> new ClearSiteDataHeaderWriter())
				.withMessage("directives cannot be empty or null");
	}

	@Test
	public void writeHeaderWhenRequestNotSecureThenHeaderIsNotPresent() {
		this.request.setSecure(false);
		ClearSiteDataHeaderWriter headerWriter = new ClearSiteDataHeaderWriter(Directive.CACHE);
		headerWriter.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(HEADER_NAME)).isNull();
	}

	@Test
	public void writeHeaderWhenRequestIsSecureThenHeaderValueMatchesPassedSource() {
		ClearSiteDataHeaderWriter headerWriter = new ClearSiteDataHeaderWriter(Directive.STORAGE);
		headerWriter.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(HEADER_NAME)).isEqualTo("\"storage\"");
	}

	@Test
	public void writeHeaderWhenRequestIsSecureThenHeaderValueMatchesPassedSources() {
		ClearSiteDataHeaderWriter headerWriter = new ClearSiteDataHeaderWriter(Directive.CACHE, Directive.COOKIES,
				Directive.STORAGE, Directive.EXECUTION_CONTEXTS);
		headerWriter.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(HEADER_NAME))
				.isEqualTo("\"cache\", \"cookies\", \"storage\", \"executionContexts\"");
	}

}
