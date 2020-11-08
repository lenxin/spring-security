package org.springframework.security.authorization;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class AuthorizationDecision {

	private final boolean granted;

	public AuthorizationDecision(boolean granted) {
		this.granted = granted;
	}

	public boolean isGranted() {
		return this.granted;
	}

}
