package org.springframework.security.authentication.jaas;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.TextInputCallback;

import org.springframework.security.core.Authentication;

/**
 * TestCallbackHandler
 *
 * @author Ray Krueger
 */
public class TestCallbackHandler implements JaasAuthenticationCallbackHandler {

	@Override
	public void handle(Callback callback, Authentication auth) {
		if (callback instanceof TextInputCallback) {
			TextInputCallback tic = (TextInputCallback) callback;
			tic.setText(auth.getPrincipal().toString());
		}
	}

}
