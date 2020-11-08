package org.springframework.security.openid;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Robin Bramley, Opsera Ltd
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
@Deprecated
public class MockOpenIDConsumer implements OpenIDConsumer {

	private OpenIDAuthenticationToken token;

	private String redirectUrl;

	public MockOpenIDConsumer() {
	}

	public MockOpenIDConsumer(String redirectUrl, OpenIDAuthenticationToken token) {
		this.redirectUrl = redirectUrl;
		this.token = token;
	}

	public MockOpenIDConsumer(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public MockOpenIDConsumer(OpenIDAuthenticationToken token) {
		this.token = token;
	}

	@Override
	public String beginConsumption(HttpServletRequest req, String claimedIdentity, String returnToUrl, String realm) {
		return this.redirectUrl;
	}

	@Override
	public OpenIDAuthenticationToken endConsumption(HttpServletRequest req) {
		return this.token;
	}

	/**
	 * Set the redirectUrl to be returned by beginConsumption
	 * @param redirectUrl
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setReturnToUrl(String returnToUrl) {
		// TODO Auto-generated method stub
	}

	/**
	 * Set the token to be returned by endConsumption
	 * @param token
	 */
	public void setToken(OpenIDAuthenticationToken token) {
		this.token = token;
	}

}
