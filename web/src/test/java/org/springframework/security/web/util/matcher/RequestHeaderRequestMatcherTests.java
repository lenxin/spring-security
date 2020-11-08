package org.springframework.security.web.util.matcher;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 *
 */
public class RequestHeaderRequestMatcherTests {

	private final String headerName = "headerName";

	private final String headerValue = "headerValue";

	private MockHttpServletRequest request;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
	}

	@Test
	public void constructorNullHeaderName() {
		assertThatIllegalArgumentException().isThrownBy(() -> new RequestHeaderRequestMatcher(null));
	}

	@Test
	public void constructorNullHeaderNameNonNullHeaderValue() {
		assertThatIllegalArgumentException().isThrownBy(() -> new RequestHeaderRequestMatcher(null, "v"));
	}

	@Test
	public void matchesHeaderNameMatches() {
		this.request.addHeader(this.headerName, this.headerValue);
		assertThat(new RequestHeaderRequestMatcher(this.headerName).matches(this.request)).isTrue();
	}

	@Test
	public void matchesHeaderNameDoesNotMatch() {
		this.request.addHeader(this.headerName + "notMatch", this.headerValue);
		assertThat(new RequestHeaderRequestMatcher(this.headerName).matches(this.request)).isFalse();
	}

	@Test
	public void matchesHeaderNameValueMatches() {
		this.request.addHeader(this.headerName, this.headerValue);
		assertThat(new RequestHeaderRequestMatcher(this.headerName, this.headerValue).matches(this.request)).isTrue();
	}

	@Test
	public void matchesHeaderNameValueHeaderNameNotMatch() {
		this.request.addHeader(this.headerName + "notMatch", this.headerValue);
		assertThat(new RequestHeaderRequestMatcher(this.headerName, this.headerValue).matches(this.request)).isFalse();
	}

	@Test
	public void matchesHeaderNameValueHeaderValueNotMatch() {
		this.request.addHeader(this.headerName, this.headerValue + "notMatch");
		assertThat(new RequestHeaderRequestMatcher(this.headerName, this.headerValue).matches(this.request)).isFalse();
	}

	@Test
	public void matchesHeaderNameValueHeaderValueMultiNotMatch() {
		this.request.addHeader(this.headerName, this.headerValue + "notMatch");
		this.request.addHeader(this.headerName, this.headerValue);
		assertThat(new RequestHeaderRequestMatcher(this.headerName, this.headerValue).matches(this.request)).isFalse();
	}

}
