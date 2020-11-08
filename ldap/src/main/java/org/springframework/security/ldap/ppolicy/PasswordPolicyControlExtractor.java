package org.springframework.security.ldap.ppolicy;

import javax.naming.directory.DirContext;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Obtains the <tt>PasswordPolicyControl</tt> from a context for use by other classes.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public final class PasswordPolicyControlExtractor {

	private static final Log logger = LogFactory.getLog(PasswordPolicyControlExtractor.class);

	private PasswordPolicyControlExtractor() {
	}

	public static PasswordPolicyResponseControl extractControl(DirContext dirCtx) {
		LdapContext ctx = (LdapContext) dirCtx;
		Control[] ctrls = null;
		try {
			ctrls = ctx.getResponseControls();
		}
		catch (javax.naming.NamingException ex) {
			logger.error("Failed to obtain response controls", ex);
		}
		for (int i = 0; ctrls != null && i < ctrls.length; i++) {
			if (ctrls[i] instanceof PasswordPolicyResponseControl) {
				return (PasswordPolicyResponseControl) ctrls[i];
			}
		}
		return null;
	}

}
