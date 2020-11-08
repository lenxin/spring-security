package org.springframework.security.oauth2.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for verifying {@link OAuth2TokenValidatorResult}
 *
 * @author Josh Cummings
 */
public class OAuth2TokenValidatorResultTests {

	private static final OAuth2Error DETAIL = new OAuth2Error("error", "description", "uri");

	@Test
	public void successWhenInvokedThenReturnsSuccessfulResult() {
		OAuth2TokenValidatorResult success = OAuth2TokenValidatorResult.success();
		assertThat(success.hasErrors()).isFalse();
	}

	@Test
	public void failureWhenInvokedWithDetailReturnsFailureResultIncludingDetail() {
		OAuth2TokenValidatorResult failure = OAuth2TokenValidatorResult.failure(DETAIL);
		assertThat(failure.hasErrors()).isTrue();
		assertThat(failure.getErrors()).containsExactly(DETAIL);
	}

	@Test
	public void failureWhenInvokedWithMultipleDetailsReturnsFailureResultIncludingAll() {
		OAuth2TokenValidatorResult failure = OAuth2TokenValidatorResult.failure(DETAIL, DETAIL);
		assertThat(failure.hasErrors()).isTrue();
		assertThat(failure.getErrors()).containsExactly(DETAIL, DETAIL);
	}

}
