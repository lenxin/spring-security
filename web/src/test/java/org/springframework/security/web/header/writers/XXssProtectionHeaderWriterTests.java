package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 * @author Ankur Pathak
 *
 */
public class XXssProtectionHeaderWriterTests {

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private XXssProtectionHeaderWriter writer;

	private static final String XSS_PROTECTION_HEADER = "X-XSS-Protection";

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.writer = new XXssProtectionHeaderWriter();
	}

	@Test
	public void writeHeaders() {
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("X-XSS-Protection")).containsOnly("1; mode=block");
	}

	@Test
	public void writeHeadersNoBlock() {
		this.writer.setBlock(false);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("X-XSS-Protection")).containsOnly("1");
	}

	@Test
	public void writeHeadersDisabled() {
		this.writer.setBlock(false);
		this.writer.setEnabled(false);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("X-XSS-Protection")).containsOnly("0");
	}

	@Test
	public void setEnabledFalseWithBlockTrue() {
		this.writer.setEnabled(false);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeaderValues("X-XSS-Protection")).containsOnly("0");
	}

	@Test
	public void setBlockTrueWithEnabledFalse() {
		this.writer.setBlock(false);
		this.writer.setEnabled(false);
		assertThatIllegalArgumentException().isThrownBy(() -> this.writer.setBlock(true));
	}

	@Test
	public void writeHeaderWhenNotPresent() {
		String value = new String("value");
		this.response.setHeader(XSS_PROTECTION_HEADER, value);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(XSS_PROTECTION_HEADER)).isSameAs(value);
	}

}
