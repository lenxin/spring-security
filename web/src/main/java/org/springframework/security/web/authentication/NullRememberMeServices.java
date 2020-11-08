package org.springframework.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/**
 * Implementation of {@link NullRememberMeServices} that does nothing.
 * <p>
 * Used as a default by several framework classes.
 * </p>
 *
 * @author Ben Alex
 */
public class NullRememberMeServices implements RememberMeServices {

	@Override
	public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public void loginFail(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public void loginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
	}

}
