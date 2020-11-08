package org.springframework.security.saml2;

/**
 * @since 5.2
 */
public class Saml2Exception extends RuntimeException {

	public Saml2Exception(String message) {
		super(message);
	}

	public Saml2Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public Saml2Exception(Throwable cause) {
		super(cause);
	}

}
