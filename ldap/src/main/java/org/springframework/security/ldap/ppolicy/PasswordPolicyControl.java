package org.springframework.security.ldap.ppolicy;

import javax.naming.ldap.Control;

/**
 *
 * A Password Policy request control.
 * <p>
 * Based on the information in the corresponding <a href=
 * "https://tools.ietf.org/draft/draft-behera-ldap-password-policy/draft-behera-ldap-password-policy-09.txt"
 * > internet draft on LDAP password policy</a>
 *
 * @author Stefan Zoerner
 * @author Luke Taylor
 * @see PasswordPolicyResponseControl
 */
public class PasswordPolicyControl implements Control {

	/**
	 * OID of the Password Policy Control
	 */
	public static final String OID = "1.3.6.1.4.1.42.2.27.8.5.1";

	private final boolean critical;

	/**
	 * Creates a non-critical (request) control.
	 */
	public PasswordPolicyControl() {
		this(Control.NONCRITICAL);
	}

	/**
	 * Creates a (request) control.
	 * @param critical indicates whether the control is critical for the client
	 */
	public PasswordPolicyControl(boolean critical) {
		this.critical = critical;
	}

	/**
	 * Retrieves the ASN.1 BER encoded value of the LDAP control. The request value for
	 * this control is always empty.
	 * @return always null
	 */
	@Override
	public byte[] getEncodedValue() {
		return null;
	}

	/**
	 * Returns the OID of the Password Policy Control ("1.3.6.1.4.1.42.2.27.8.5.1").
	 */
	@Override
	public String getID() {
		return OID;
	}

	/**
	 * Returns whether the control is critical for the client.
	 */
	@Override
	public boolean isCritical() {
		return this.critical;
	}

}
