package org.springframework.security.oauth2.core;

import java.io.Serializable;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * A representation of an OAuth 2.0 Error.
 *
 * <p>
 * At a minimum, an error response will contain an error code. The error code may be one
 * of the standard codes defined by the specification, or a new code defined in the OAuth
 * Extensions Error Registry, for cases where protocol extensions require additional error
 * code(s) above the standard codes.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see OAuth2ErrorCodes
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-11.4">Section
 * 11.4 OAuth Extensions Error Registry</a>
 */
public class OAuth2Error implements Serializable {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String errorCode;

	private final String description;

	private final String uri;

	/**
	 * Constructs an {@code OAuth2Error} using the provided parameters.
	 * @param errorCode the error code
	 */
	public OAuth2Error(String errorCode) {
		this(errorCode, null, null);
	}

	/**
	 * Constructs an {@code OAuth2Error} using the provided parameters.
	 * @param errorCode the error code
	 * @param description the error description
	 * @param uri the error uri
	 */
	public OAuth2Error(String errorCode, String description, String uri) {
		Assert.hasText(errorCode, "errorCode cannot be empty");
		this.errorCode = errorCode;
		this.description = description;
		this.uri = uri;
	}

	/**
	 * Returns the error code.
	 * @return the error code
	 */
	public final String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Returns the error description.
	 * @return the error description
	 */
	public final String getDescription() {
		return this.description;
	}

	/**
	 * Returns the error uri.
	 * @return the error uri
	 */
	public final String getUri() {
		return this.uri;
	}

	@Override
	public String toString() {
		return "[" + this.getErrorCode() + "] " + ((this.getDescription() != null) ? this.getDescription() : "");
	}

}
