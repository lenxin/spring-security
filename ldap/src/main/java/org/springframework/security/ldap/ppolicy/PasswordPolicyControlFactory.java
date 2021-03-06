package org.springframework.security.ldap.ppolicy;

import javax.naming.ldap.Control;
import javax.naming.ldap.ControlFactory;

/**
 * Transforms a control object to a PasswordPolicyResponseControl object, if appropriate.
 *
 * @author Stefan Zoerner
 * @author Luke Taylor
 */
public class PasswordPolicyControlFactory extends ControlFactory {

	/**
	 * Creates an instance of PasswordPolicyResponseControl if the passed control is a
	 * response control of this type. Attributes of the result are filled with the correct
	 * values (e.g. error code).
	 * @param ctl the control the check
	 * @return a response control of type PasswordPolicyResponseControl, or null
	 */
	@Override
	public Control getControlInstance(Control ctl) {
		if (ctl.getID().equals(PasswordPolicyControl.OID)) {
			return new PasswordPolicyResponseControl(ctl.getEncodedValue());
		}
		return null;
	}

}
