package org.springframework.security.oauth2.core;

import java.io.Serializable;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * The authentication method used when sending bearer access tokens in resource requests
 * to resource servers.
 *
 * @author MyeongHyeon Lee
 * @since 5.1
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6750#section-2">Section 2
 * Authenticated Requests</a>
 */
public final class AuthenticationMethod implements Serializable {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public static final AuthenticationMethod HEADER = new AuthenticationMethod("header");

	public static final AuthenticationMethod FORM = new AuthenticationMethod("form");

	public static final AuthenticationMethod QUERY = new AuthenticationMethod("query");

	private final String value;

	/**
	 * Constructs an {@code AuthenticationMethod} using the provided value.
	 * @param value the value of the authentication method type
	 */
	public AuthenticationMethod(String value) {
		Assert.hasText(value, "value cannot be empty");
		this.value = value;
	}

	/**
	 * Returns the value of the authentication method type.
	 * @return the value of the authentication method type
	 */
	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		AuthenticationMethod that = (AuthenticationMethod) obj;
		return this.getValue().equals(that.getValue());
	}

	@Override
	public int hashCode() {
		return this.getValue().hashCode();
	}

}
