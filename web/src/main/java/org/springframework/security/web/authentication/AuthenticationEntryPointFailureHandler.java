package org.springframework.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

/**
 * Adapts a {@link AuthenticationEntryPoint} into a {@link AuthenticationFailureHandler}
 *
 * @author Sergey Bespalov
 * @since 5.2.0
 */
public class AuthenticationEntryPointFailureHandler implements AuthenticationFailureHandler {

	private final AuthenticationEntryPoint authenticationEntryPoint;

	public AuthenticationEntryPointFailureHandler(AuthenticationEntryPoint authenticationEntryPoint) {
		Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		this.authenticationEntryPoint.commence(request, response, exception);
	}

}
