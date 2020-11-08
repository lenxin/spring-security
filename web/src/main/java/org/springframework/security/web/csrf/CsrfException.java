package org.springframework.security.web.csrf;

import org.springframework.security.access.AccessDeniedException;

/**
 * Thrown when an invalid or missing {@link CsrfToken} is found in the HttpServletRequest
 *
 * @author Rob Winch
 * @since 3.2
 */
@SuppressWarnings("serial")
public class CsrfException extends AccessDeniedException {

	public CsrfException(String message) {
		super(message);
	}

}
