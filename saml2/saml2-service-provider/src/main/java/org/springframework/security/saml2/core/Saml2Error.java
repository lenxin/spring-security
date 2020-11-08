package org.springframework.security.saml2.core;

import java.io.Serializable;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * A representation of an SAML 2.0 Error.
 *
 * <p>
 * At a minimum, an error response will contain an error code. The commonly used error
 * code are defined in this class or a new codes can be defined in the future as arbitrary
 * strings.
 * </p>
 *
 * @since 5.2
 */
public class Saml2Error implements Serializable {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String errorCode;

	private final String description;

	/**
	 * Constructs a {@code Saml2Error} using the provided parameters.
	 * @param errorCode the error code
	 * @param description the error description
	 */
	public Saml2Error(String errorCode, String description) {
		Assert.hasText(errorCode, "errorCode cannot be empty");
		this.errorCode = errorCode;
		this.description = description;
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

	@Override
	public String toString() {
		return "[" + this.getErrorCode() + "] " + ((this.getDescription() != null) ? this.getDescription() : "");
	}

}
