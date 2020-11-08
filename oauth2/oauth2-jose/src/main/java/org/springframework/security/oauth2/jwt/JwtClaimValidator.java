package org.springframework.security.oauth2.jwt;

import java.util.function.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.util.Assert;

/**
 * Validates a claim in a {@link Jwt} against a provided
 * {@link java.util.function.Predicate}
 *
 * @author Zeeshan Adnan
 * @since 5.3
 */
public final class JwtClaimValidator<T> implements OAuth2TokenValidator<Jwt> {

	private final Log logger = LogFactory.getLog(getClass());

	private final String claim;

	private final Predicate<T> test;

	private final OAuth2Error error;

	/**
	 * Constructs a {@link JwtClaimValidator} using the provided parameters
	 * @param claim - is the name of the claim in {@link Jwt} to validate.
	 * @param test - is the predicate function for the claim to test against.
	 */
	public JwtClaimValidator(String claim, Predicate<T> test) {
		Assert.notNull(claim, "claim can not be null");
		Assert.notNull(test, "test can not be null");
		this.claim = claim;
		this.test = test;
		this.error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "The " + this.claim + " claim is not valid",
				"https://tools.ietf.org/html/rfc6750#section-3.1");
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt token) {
		Assert.notNull(token, "token cannot be null");
		T claimValue = token.getClaim(this.claim);
		if (this.test.test(claimValue)) {
			return OAuth2TokenValidatorResult.success();
		}
		this.logger.debug(this.error.getDescription());
		return OAuth2TokenValidatorResult.failure(this.error);
	}

}
