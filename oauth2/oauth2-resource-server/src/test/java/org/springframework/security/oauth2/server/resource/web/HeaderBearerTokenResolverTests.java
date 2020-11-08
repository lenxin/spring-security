package org.springframework.security.oauth2.server.resource.web;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link HeaderBearerTokenResolver}
 *
 * @author Elena Felder
 */
public class HeaderBearerTokenResolverTests {

	private static final String TEST_TOKEN = "test-token";

	private static final String CORRECT_HEADER = "jwt-assertion";

	private HeaderBearerTokenResolver resolver = new HeaderBearerTokenResolver(CORRECT_HEADER);

	@Test
	public void constructorWhenHeaderNullThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new HeaderBearerTokenResolver(null))
				.withMessage("header cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWhenHeaderEmptyThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new HeaderBearerTokenResolver(""))
				.withMessage("header cannot be empty");
		// @formatter:on
	}

	@Test
	public void resolveWhenTokenPresentThenTokenIsResolved() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(CORRECT_HEADER, TEST_TOKEN);
		assertThat(this.resolver.resolve(request)).isEqualTo(TEST_TOKEN);
	}

	@Test
	public void resolveWhenTokenNotPresentThenTokenIsNotResolved() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertThat(this.resolver.resolve(request)).isNull();
	}

}
