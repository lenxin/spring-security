package org.springframework.security.authentication.jaas;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.PasswordCallback;

import org.springframework.security.core.Authentication;

/**
 * The most basic Callbacks to be handled when using a LoginContext from JAAS, are the
 * NameCallback and PasswordCallback. Spring Security provides the
 * JaasPasswordCallbackHandler specifically tailored to handling the PasswordCallback.
 * <br>
 *
 * @author Ray Krueger
 * @see <a href=
 * "https://java.sun.com/j2se/1.4.2/docs/api/javax/security/auth/callback/Callback.html">Callback</a>
 * @see <a href=
 * "https://java.sun.com/j2se/1.4.2/docs/api/javax/security/auth/callback/PasswordCallback.html">
 * PasswordCallback</a>
 */
public class JaasPasswordCallbackHandler implements JaasAuthenticationCallbackHandler {

	/**
	 * If the callback passed to the 'handle' method is an instance of PasswordCallback,
	 * the JaasPasswordCallbackHandler will call,
	 * callback.setPassword(authentication.getCredentials().toString()).
	 * @param callback
	 * @param auth
	 *
	 */
	@Override
	public void handle(Callback callback, Authentication auth) {
		if (callback instanceof PasswordCallback) {
			((PasswordCallback) callback).setPassword(auth.getCredentials().toString().toCharArray());
		}
	}

}
