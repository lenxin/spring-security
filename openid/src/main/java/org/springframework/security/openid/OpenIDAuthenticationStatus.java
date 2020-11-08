package org.springframework.security.openid;

/**
 * Authentication status codes, based on JanRain status codes
 * @author JanRain Inc.
 * @author Robin Bramley, Opsera Ltd
 * @author Luke Taylor
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
public enum OpenIDAuthenticationStatus {

	/** This code indicates a successful authentication request */
	SUCCESS("success"),

	/** This code indicates a failed authentication request */
	FAILURE("failure"),

	/** This code indicates the server reported an error */
	ERROR("error"),

	/**
	 * This code indicates that the user needs to do additional work to prove their
	 * identity
	 */
	SETUP_NEEDED("setup needed"),

	/** This code indicates that the user cancelled their login request */
	CANCELLED("cancelled");

	private final String name;

	OpenIDAuthenticationStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
