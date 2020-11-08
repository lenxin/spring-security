package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Joe Grandja
 * @author Ankur Pathak
 */
public class ContentSecurityPolicyHeaderWriterTests {

	private static final String DEFAULT_POLICY_DIRECTIVES = "default-src 'self'";

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private ContentSecurityPolicyHeaderWriter writer;

	private static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";

	private static final String CONTENT_SECURITY_POLICY_REPORT_ONLY_HEADER = "Content-Security-Policy-Report-Only";

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.request.setSecure(true);
		this.response = new MockHttpServletResponse();
		this.writer = new ContentSecurityPolicyHeaderWriter(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersWhenNoPolicyDirectivesThenUsesDefault() {
		ContentSecurityPolicyHeaderWriter noPolicyWriter = new ContentSecurityPolicyHeaderWriter();
		noPolicyWriter.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Content-Security-Policy")).isEqualTo(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersContentSecurityPolicyDefault() {
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Content-Security-Policy")).isEqualTo(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersContentSecurityPolicyCustom() {
		String policyDirectives = "default-src 'self'; " + "object-src plugins1.example.com plugins2.example.com; "
				+ "script-src trustedscripts.example.com";
		this.writer = new ContentSecurityPolicyHeaderWriter(policyDirectives);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Content-Security-Policy")).isEqualTo(policyDirectives);
	}

	@Test
	public void writeHeadersWhenNoPolicyDirectivesReportOnlyThenUsesDefault() {
		ContentSecurityPolicyHeaderWriter noPolicyWriter = new ContentSecurityPolicyHeaderWriter();
		this.writer.setReportOnly(true);
		noPolicyWriter.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Content-Security-Policy")).isEqualTo(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersContentSecurityPolicyReportOnlyDefault() {
		this.writer.setReportOnly(true);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Content-Security-Policy-Report-Only")).isEqualTo(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersContentSecurityPolicyReportOnlyCustom() {
		String policyDirectives = "default-src https:; report-uri https://example.com/";
		this.writer = new ContentSecurityPolicyHeaderWriter(policyDirectives);
		this.writer.setReportOnly(true);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Content-Security-Policy-Report-Only")).isEqualTo(policyDirectives);
	}

	@Test
	public void writeHeadersContentSecurityPolicyInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ContentSecurityPolicyHeaderWriter(""));
		assertThatIllegalArgumentException().isThrownBy(() -> new ContentSecurityPolicyHeaderWriter(null));
	}

	@Test
	public void writeContentSecurityPolicyHeaderWhenNotPresent() {
		String value = new String("value");
		this.response.setHeader(CONTENT_SECURITY_POLICY_HEADER, value);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(CONTENT_SECURITY_POLICY_HEADER)).isSameAs(value);
	}

	@Test
	public void writeContentSecurityPolicyReportOnlyHeaderWhenNotPresent() {
		String value = new String("value");
		this.response.setHeader(CONTENT_SECURITY_POLICY_REPORT_ONLY_HEADER, value);
		this.writer.setReportOnly(true);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(CONTENT_SECURITY_POLICY_REPORT_ONLY_HEADER)).isSameAs(value);
	}

}
