package org.springframework.security.saml2.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for verifying {@link Saml2ResponseValidatorResult}
 *
 * @author Josh Cummings
 */
public class Saml2ResponseValidatorResultTests {

	private static final Saml2Error DETAIL = new Saml2Error("error", "description");

	@Test
	public void successWhenInvokedThenReturnsSuccessfulResult() {
		Saml2ResponseValidatorResult success = Saml2ResponseValidatorResult.success();
		assertThat(success.hasErrors()).isFalse();
	}

	@Test
	public void failureWhenInvokedWithDetailReturnsFailureResultIncludingDetail() {
		Saml2ResponseValidatorResult failure = Saml2ResponseValidatorResult.failure(DETAIL);

		assertThat(failure.hasErrors()).isTrue();
		assertThat(failure.getErrors()).containsExactly(DETAIL);
	}

	@Test
	public void failureWhenInvokedWithMultipleDetailsReturnsFailureResultIncludingAll() {
		Saml2ResponseValidatorResult failure = Saml2ResponseValidatorResult.failure(DETAIL, DETAIL);

		assertThat(failure.hasErrors()).isTrue();
		assertThat(failure.getErrors()).containsExactly(DETAIL, DETAIL);
	}

	@Test
	public void concatErrorWhenInvokedThenReturnsCopyContainingAll() {
		Saml2ResponseValidatorResult failure = Saml2ResponseValidatorResult.failure(DETAIL);
		Saml2ResponseValidatorResult added = failure.concat(DETAIL);

		assertThat(added.hasErrors()).isTrue();
		assertThat(added.getErrors()).containsExactly(DETAIL, DETAIL);
		assertThat(failure).isNotSameAs(added);
	}

	@Test
	public void concatResultWhenInvokedThenReturnsCopyContainingAll() {
		Saml2ResponseValidatorResult failure = Saml2ResponseValidatorResult.failure(DETAIL);
		Saml2ResponseValidatorResult merged = failure.concat(failure).concat(failure);

		assertThat(merged.hasErrors()).isTrue();
		assertThat(merged.getErrors()).containsExactly(DETAIL, DETAIL, DETAIL);
		assertThat(failure).isNotSameAs(merged);
	}

	@Test
	public void concatErrorWhenNullThenIllegalArgument() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Saml2ResponseValidatorResult.failure(DETAIL)
						.concat((Saml2Error) null)
				);
		// @formatter:on
	}

	@Test
	public void concatResultWhenNullThenIllegalArgument() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Saml2ResponseValidatorResult.failure(DETAIL)
						.concat((Saml2ResponseValidatorResult) null)
				);
		// @formatter:on
	}

}
