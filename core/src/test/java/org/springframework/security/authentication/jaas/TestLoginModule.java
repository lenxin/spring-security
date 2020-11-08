package org.springframework.security.authentication.jaas;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * @author Ray Krueger
 */
public class TestLoginModule implements LoginModule {

	private String password;

	private String user;

	private Subject subject;

	@Override
	public boolean abort() {
		return true;
	}

	@Override
	public boolean commit() {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		this.subject = subject;
		try {
			TextInputCallback textCallback = new TextInputCallback("prompt");
			NameCallback nameCallback = new NameCallback("prompt");
			PasswordCallback passwordCallback = new PasswordCallback("prompt", false);
			callbackHandler.handle(new Callback[] { textCallback, nameCallback, passwordCallback });
			this.password = new String(passwordCallback.getPassword());
			this.user = nameCallback.getName();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean login() throws LoginException {
		if (!this.user.equals("user")) {
			throw new LoginException("Bad User");
		}
		if (!this.password.equals("password")) {
			throw new LoginException("Bad Password");
		}
		this.subject.getPrincipals().add(() -> "TEST_PRINCIPAL");
		this.subject.getPrincipals().add(() -> "NULL_PRINCIPAL");
		return true;
	}

	@Override
	public boolean logout() {
		return true;
	}

}
