package org.springframework.security.samples.servletapi.mvc;

/**
 *
 * @author Rob Winch
 *
 */
public class LoginForm {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
