package org.springframework.security.web.header.writers;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link FeaturePolicyHeaderWriter}.
 *
 * @author Vedran Pavic
 * @author Ankur Pathak
 */
public class FeaturePolicyHeaderWriterTests {

	private static final String DEFAULT_POLICY_DIRECTIVES = "geolocation 'self'";

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private FeaturePolicyHeaderWriter writer;

	private static final String FEATURE_POLICY_HEADER = "Feature-Policy";

	@Before
	public void setUp() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.writer = new FeaturePolicyHeaderWriter(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void writeHeadersFeaturePolicyDefault() {
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeaderNames()).hasSize(1);
		assertThat(this.response.getHeader("Feature-Policy")).isEqualTo(DEFAULT_POLICY_DIRECTIVES);
	}

	@Test
	public void createWriterWithNullDirectivesShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new FeaturePolicyHeaderWriter(null))
				.withMessage("policyDirectives must not be null or empty");
	}

	@Test
	public void createWriterWithEmptyDirectivesShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new FeaturePolicyHeaderWriter(""))
				.withMessage("policyDirectives must not be null or empty");
	}

	@Test
	public void writeHeaderOnlyIfNotPresent() {
		String value = new String("value");
		this.response.setHeader(FEATURE_POLICY_HEADER, value);
		this.writer.writeHeaders(this.request, this.response);
		assertThat(this.response.getHeader(FEATURE_POLICY_HEADER)).isSameAs(value);
	}

}
