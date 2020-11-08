package org.springframework.security.oauth2.jwt;

import java.util.function.Predicate;

import org.junit.Test;

import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link JwtClaimValidator}.
 *
 * @author Zeeshan Adnan
 */
public class JwtClaimValidatorTests {

	private static final Predicate<String> test = (claim) -> claim.equals("http://test");

	private final JwtClaimValidator<String> validator = new JwtClaimValidator<>(JwtClaimNames.ISS, test);

	@Test
	public void validateWhenClaimPassesTheTestThenReturnsSuccess() {
		Jwt jwt = TestJwts.jwt().claim(JwtClaimNames.ISS, "http://test").build();
		assertThat(this.validator.validate(jwt)).isEqualTo(OAuth2TokenValidatorResult.success());
	}

	@Test
	public void validateWhenClaimFailsTheTestThenReturnsFailure() {
		Jwt jwt = TestJwts.jwt().claim(JwtClaimNames.ISS, "http://abc").build();
		assertThat(this.validator.validate(jwt).getErrors().isEmpty()).isFalse();
	}

	@Test
	public void validateWhenClaimIsNullThenThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new JwtClaimValidator<>(null, test));
	}

	@Test
	public void validateWhenTestIsNullThenThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new JwtClaimValidator<>(JwtClaimNames.ISS, null));
	}

	@Test
	public void validateWhenJwtIsNullThenThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.validator.validate(null));
	}

}
