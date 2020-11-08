package org.springframework.security.openid;

import org.springframework.security.core.AuthenticationException;

/**
 * Indicates that OpenID authentication was cancelled
 *
 * @author Robin Bramley, Opsera Ltd
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
@Deprecated
public class AuthenticationCancelledException extends AuthenticationException {

	public AuthenticationCancelledException(String msg) {
		super(msg);
	}

	public AuthenticationCancelledException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
