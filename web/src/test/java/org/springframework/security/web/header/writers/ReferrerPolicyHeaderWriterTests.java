package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Eddú Meléndez
 * @author Ankur Pathak
 */
public class ReferrerPolicyHeaderWriterTests {

	private final String DEFAULT_REFERRER_POLICY = "no-referrer";

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private ReferrerPolicyHeaderWriter writer;

	private static final String REFERRER_POLICY_HEADER = "Referrer-Policy";

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.request.setSecure(true);
		this.response = new MockHttpServletResponse();
		this.writer = new ReferrerPolicyHeaderWriter();
	}

	@Test
	public void writeHeadersReferrerPolicyDefault() {
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Referrer-Policy")).isEqualTo(this.DEFAULT_REFERRER_POLICY);
	}

	@Test
	public void writeHeadersReferrerPolicyCustom() {
		this.writer = new ReferrerPolicyHeaderWriter(ReferrerPolicy.SAME_ORIGIN);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Referrer-Policy")).isEqualTo("same-origin");
	}

	@Test
	public void writeHeaderReferrerPolicyInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ReferrerPolicyHeaderWriter(null));
	}

	@Test
	public void writeHeaderWhenNotPresent() {
		String value = new String("value");
		this.response.setHeader(REFERRER_POLICY_HEADER, value);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(REFERRER_POLICY_HEADER)).isSameAs(value);
	}

}
