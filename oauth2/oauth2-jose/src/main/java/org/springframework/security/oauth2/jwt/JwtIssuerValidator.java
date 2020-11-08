package org.springframework.security.oauth2.jwt;

import java.util.function.Predicate;

import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.util.Assert;

/**
 * Validates the "iss" claim in a {@link Jwt}, that is matches a configured value
 *
 * @author Josh Cummings
 * @since 5.1
 */
public final class JwtIssuerValidator implements OAuth2TokenValidator<Jwt> {

	private final JwtClaimValidator<Object> validator;

	/**
	 * Constructs a {@link JwtIssuerValidator} using the provided parameters
	 * @param issuer - The issuer that each {@link Jwt} should have.
	 */
	public JwtIssuerValidator(String issuer) {
		Assert.notNull(issuer, "issuer cannot be null");

		Predicate<Object> testClaimValue = (claimValue) -> (claimValue != null) && issuer.equals(claimValue.toString());
		this.validator = new JwtClaimValidator<>(JwtClaimNames.ISS, testClaimValue);
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt token) {
		Assert.notNull(token, "token cannot be null");
		return this.validator.validate(token);
	}

}
