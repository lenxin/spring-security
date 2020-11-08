package org.springframework.security.ldap.ppolicy;

import javax.naming.ldap.Control;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 */
public class PasswordPolicyControlFactoryTests {

	@Test
	public void returnsNullForUnrecognisedOID() {
		PasswordPolicyControlFactory ctrlFactory = new PasswordPolicyControlFactory();
		Control wrongCtrl = mock(Control.class);
		given(wrongCtrl.getID()).willReturn("wrongId");
		assertThat(ctrlFactory.getControlInstance(wrongCtrl)).isNull();
	}

	@Test
	public void returnsControlForCorrectOID() {
		PasswordPolicyControlFactory ctrlFactory = new PasswordPolicyControlFactory();
		Control control = mock(Control.class);
		given(control.getID()).willReturn(PasswordPolicyControl.OID);
		given(control.getEncodedValue()).willReturn(PasswordPolicyResponseControlTests.OPENLDAP_LOCKED_CTRL);
		Control result = ctrlFactory.getControlInstance(control);
		assertThat(result).isNotNull();
		assertThat(PasswordPolicyResponseControlTests.OPENLDAP_LOCKED_CTRL).isEqualTo(result.getEncodedValue());
	}

}
