package org.springframework.security.oauth2.jwt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.util.Assert;

/**
 * An exception that results from an unsuccessful {@link OAuth2TokenValidatorResult}
 *
 * @author Josh Cummings
 * @since 5.1
 */
public class JwtValidationException extends BadJwtException {

	private final Collection<OAuth2Error> errors;

	/**
	 * Constructs a {@link JwtValidationException} using the provided parameters
	 *
	 * While each {@link OAuth2Error} does contain an error description, this constructor
	 * can take an overarching description that encapsulates the composition of failures
	 *
	 * That said, it is appropriate to pass one of the messages from the error list in as
	 * the exception description, for example:
	 *
	 * <pre>
	 * 	if ( result.hasErrors() ) {
	 *  	Collection&lt;OAuth2Error&gt; errors = result.getErrors();
	 *  	throw new JwtValidationException(errors.iterator().next().getDescription(), errors);
	 * 	}
	 * </pre>
	 * @param message - the exception message
	 * @param errors - a list of {@link OAuth2Error}s with extra detail about the
	 * validation result
	 */
	public JwtValidationException(String message, Collection<OAuth2Error> errors) {
		super(message);
		Assert.notEmpty(errors, "errors cannot be empty");
		this.errors = new ArrayList<>(errors);
	}

	/**
	 * Return the list of {@link OAuth2Error}s associated with this exception
	 * @return the list of {@link OAuth2Error}s associated with this exception
	 */
	public Collection<OAuth2Error> getErrors() {
		return this.errors;
	}

}
