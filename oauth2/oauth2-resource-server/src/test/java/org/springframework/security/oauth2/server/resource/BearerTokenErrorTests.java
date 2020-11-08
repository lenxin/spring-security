package org.springframework.security.oauth2.server.resource;

import org.junit.Test;

import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link BearerTokenError}
 *
 * @author Vedran Pavic
 * @author Josh Cummings
 */
public class BearerTokenErrorTests {

	private static final String TEST_ERROR_CODE = "test-code";

	private static final HttpStatus TEST_HTTP_STATUS = HttpStatus.UNAUTHORIZED;

	private static final String TEST_DESCRIPTION = "test-description";

	private static final String TEST_URI = "https://example.com";

	private static final String TEST_SCOPE = "test-scope";

	@Test
	public void constructorWithErrorCodeWhenErrorCodeIsValidThenCreated() {
		BearerTokenError error = new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS, null, null);
		assertThat(error.getErrorCode()).isEqualTo(TEST_ERROR_CODE);
		assertThat(error.getHttpStatus()).isEqualTo(TEST_HTTP_STATUS);
		assertThat(error.getDescription()).isNull();
		assertThat(error.getUri()).isNull();
		assertThat(error.getScope()).isNull();
	}

	@Test
	public void constructorWithErrorCodeAndHttpStatusWhenErrorCodeIsNullThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(null, TEST_HTTP_STATUS, null, null))
				.withMessage("errorCode cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWithErrorCodeAndHttpStatusWhenErrorCodeIsEmptyThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError("", TEST_HTTP_STATUS, null, null))
				.withMessage("errorCode cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWithErrorCodeAndHttpStatusWhenHttpStatusIsNullThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(TEST_ERROR_CODE, null, null, null))
				.withMessage("httpStatus cannot be null");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenAllParametersAreValidThenCreated() {
		BearerTokenError error = new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI,
				TEST_SCOPE);
		assertThat(error.getErrorCode()).isEqualTo(TEST_ERROR_CODE);
		assertThat(error.getHttpStatus()).isEqualTo(TEST_HTTP_STATUS);
		assertThat(error.getDescription()).isEqualTo(TEST_DESCRIPTION);
		assertThat(error.getUri()).isEqualTo(TEST_URI);
		assertThat(error.getScope()).isEqualTo(TEST_SCOPE);
	}

	@Test
	public void constructorWithAllParametersWhenErrorCodeIsNullThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(null, TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE))
				.withMessage("errorCode cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenErrorCodeIsEmptyThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError("", TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE))
				.withMessage("errorCode cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenHttpStatusIsNullThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(TEST_ERROR_CODE, null, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE))
				.withMessage("httpStatus cannot be null");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenErrorCodeIsInvalidThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(TEST_ERROR_CODE + "\"",
					TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE)
				)
				.withMessageContaining("errorCode")
				.withMessageContaining("RFC 6750");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenDescriptionIsInvalidThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS,
					TEST_DESCRIPTION + "\"", TEST_URI, TEST_SCOPE)
				)
				.withMessageContaining("description")
				.withMessageContaining("RFC 6750");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenErrorUriIsInvalidThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS, TEST_DESCRIPTION,
						TEST_URI + "\"", TEST_SCOPE)
				)
				.withMessageContaining("errorUri")
				.withMessageContaining("RFC 6750");
		// @formatter:on
	}

	@Test
	public void constructorWithAllParametersWhenScopeIsInvalidThenThrowIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS,
					TEST_DESCRIPTION, TEST_URI, TEST_SCOPE + "\"")
				)
				.withMessageContaining("scope")
				.withMessageContaining("RFC 6750");
		// @formatter:on
	}

}
