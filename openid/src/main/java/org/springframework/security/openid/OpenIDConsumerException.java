package org.springframework.security.openid;

/**
 * Thrown by an OpenIDConsumer if it cannot process a request
 *
 * @author Robin Bramley, Opsera Ltd
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
@Deprecated
public class OpenIDConsumerException extends Exception {

	public OpenIDConsumerException(String message) {
		super(message);
	}

	public OpenIDConsumerException(String message, Throwable cause) {
		super(message, cause);
	}

}
