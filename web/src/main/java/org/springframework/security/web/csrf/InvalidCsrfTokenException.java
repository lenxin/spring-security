package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;

/**
 * Thrown when an expected {@link CsrfToken} exists, but it does not match the value
 * present on the {@link HttpServletRequest}
 *
 * @author Rob Winch
 * @since 3.2
 */
@SuppressWarnings("serial")
public class InvalidCsrfTokenException extends CsrfException {

	/**
	 * @param expectedAccessToken
	 * @param actualAccessToken
	 */
	public InvalidCsrfTokenException(CsrfToken expectedAccessToken, String actualAccessToken) {
		super("Invalid CSRF Token '" + actualAccessToken + "' was found on the request parameter '"
				+ expectedAccessToken.getParameterName() + "' or header '" + expectedAccessToken.getHeaderName()
				+ "'.");
	}

}
