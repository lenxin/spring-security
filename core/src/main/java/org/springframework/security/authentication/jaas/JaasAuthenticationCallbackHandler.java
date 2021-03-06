package org.springframework.security.authentication.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.springframework.security.core.Authentication;

/**
 * The JaasAuthenticationCallbackHandler is similar to the
 * javax.security.auth.callback.CallbackHandler interface in that it defines a handle
 * method. The JaasAuthenticationCallbackHandler is only asked to handle one Callback
 * instance at time rather than an array of all Callbacks, as the javax... CallbackHandler
 * defines.
 *
 * <p>
 * Before a JaasAuthenticationCallbackHandler is asked to 'handle' any callbacks, it is
 * first passed the Authentication object that the login attempt is for. NOTE: The
 * Authentication object has not been 'authenticated' yet.
 * </p>
 *
 * @author Ray Krueger
 * @see JaasNameCallbackHandler
 * @see JaasPasswordCallbackHandler
 * @see <a href=
 * "https://java.sun.com/j2se/1.4.2/docs/api/javax/security/auth/callback/Callback.html">Callback</a>
 * @see <a href=
 * "https://java.sun.com/j2se/1.4.2/docs/api/javax/security/auth/callback/CallbackHandler.html">
 * CallbackHandler</a>
 */
public interface JaasAuthenticationCallbackHandler {

	/**
	 * Handle the <a href=
	 * "https://java.sun.com/j2se/1.4.2/docs/api/javax/security/auth/callback/Callback.html"
	 * >Callback</a>. The handle method will be called for every callback instance sent
	 * from the LoginContext. Meaning that The handle method may be called multiple times
	 * for a given JaasAuthenticationCallbackHandler.
	 * @param callback
	 * @param auth The Authentication object currently being authenticated.
	 *
	 */
	void handle(Callback callback, Authentication auth) throws IOException, UnsupportedCallbackException;

}
