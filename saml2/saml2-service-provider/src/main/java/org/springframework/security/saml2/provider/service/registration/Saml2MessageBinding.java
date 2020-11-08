package org.springframework.security.saml2.provider.service.registration;

/**
 * The type of bindings that messages are exchanged using Supported bindings are
 * {@code urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST} and
 * {@code urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect}. In addition there is
 * support for {@code urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect} with an XML
 * signature in the message rather than query parameters.
 * @since 5.3
 */
public enum Saml2MessageBinding {

	POST("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"), REDIRECT(
			"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect");

	private final String urn;

	Saml2MessageBinding(String s) {
		this.urn = s;
	}

	/**
	 * Returns the URN value from the SAML 2 specification for this binding.
	 * @return URN value representing this binding
	 */
	public String getUrn() {
		return this.urn;
	}

}
