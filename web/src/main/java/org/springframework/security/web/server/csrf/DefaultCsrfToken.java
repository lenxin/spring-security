package org.springframework.security.web.server.csrf;

import org.springframework.util.Assert;

/**
 * A CSRF token that is used to protect against CSRF attacks.
 *
 * @author Rob Winch
 * @since 5.0
 */
@SuppressWarnings("serial")
public final class DefaultCsrfToken implements CsrfToken {

	private final String token;

	private final String parameterName;

	private final String headerName;

	/**
	 * Creates a new instance
	 * @param headerName the HTTP header name to use
	 * @param parameterName the HTTP parameter name to use
	 * @param token the value of the token (i.e. expected value of the HTTP parameter of
	 * parametername).
	 */
	public DefaultCsrfToken(String headerName, String parameterName, String token) {
		Assert.hasLength(headerName, "headerName cannot be null or empty");
		Assert.hasLength(parameterName, "parameterName cannot be null or empty");
		Assert.hasLength(token, "token cannot be null or empty");
		this.headerName = headerName;
		this.parameterName = parameterName;
		this.token = token;
	}

	@Override
	public String getHeaderName() {
		return this.headerName;
	}

	@Override
	public String getParameterName() {
		return this.parameterName;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof CsrfToken)) {
			return false;
		}
		CsrfToken other = (CsrfToken) obj;
		if (!getToken().equals(other.getToken())) {
			return false;
		}
		if (!getParameterName().equals(other.getParameterName())) {
			return false;
		}
		return getHeaderName().equals(other.getHeaderName());
	}

	@Override
	public int hashCode() {
		int result = getToken().hashCode();
		result = 31 * result + getParameterName().hashCode();
		result = 31 * result + getHeaderName().hashCode();
		return result;
	}

}
