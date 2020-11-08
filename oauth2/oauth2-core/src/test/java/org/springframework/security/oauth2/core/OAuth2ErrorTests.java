package org.springframework.security.oauth2.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OAuth2Error}.
 *
 * @author Joe Grandja
 */
public class OAuth2ErrorTests {

	private static final String ERROR_CODE = "error-code";

	private static final String ERROR_DESCRIPTION = "error-description";

	private static final String ERROR_URI = "error-uri";

	@Test
	public void constructorWhenErrorCodeIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OAuth2Error(null, ERROR_DESCRIPTION, ERROR_URI));
	}

	@Test
	public void constructorWhenAllParametersProvidedAndValidThenCreated() {
		OAuth2Error error = new OAuth2Error(ERROR_CODE, ERROR_DESCRIPTION, ERROR_URI);
		assertThat(error.getErrorCode()).isEqualTo(ERROR_CODE);
		assertThat(error.getDescription()).isEqualTo(ERROR_DESCRIPTION);
		assertThat(error.getUri()).isEqualTo(ERROR_URI);
	}

}
