package org.springframework.security.web.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * A strategy used for converting from a {@link HttpServletRequest} to an
 * {@link Authentication} of particular type. Used to authenticate with appropriate
 * {@link AuthenticationManager}. If the result is null, then it signals that no
 * authentication attempt should be made. It is also possible to throw
 * {@link AuthenticationException} within the {@link #convert(HttpServletRequest)} if
 * there was invalid Authentication scheme value.
 *
 * @author Sergey Bespalov
 * @since 5.2.0
 */
public interface AuthenticationConverter {

	Authentication convert(HttpServletRequest request);

}
