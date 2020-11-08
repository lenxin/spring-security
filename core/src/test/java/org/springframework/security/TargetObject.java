package org.springframework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Represents a secured object.
 *
 * @author Ben Alex
 */
public class TargetObject implements ITargetObject {

	@Override
	public Integer computeHashCode(String input) {
		return input.hashCode();
	}

	@Override
	public int countLength(String input) {
		return input.length();
	}

	/**
	 * Returns the lowercase string, followed by security environment information.
	 * @param input the message to make lowercase
	 * @return the lowercase message, a space, the <code>Authentication</code> class that
	 * was on the <code>SecurityContext</code> at the time of method invocation, and a
	 * boolean indicating if the <code>Authentication</code> object is authenticated or
	 * not
	 */
	@Override
	public String makeLowerCase(String input) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return input.toLowerCase() + " Authentication empty";
		}
		else {
			return input.toLowerCase() + " " + auth.getClass().getName() + " " + auth.isAuthenticated();
		}
	}

	/**
	 * Returns the uppercase string, followed by security environment information.
	 * @param input the message to make uppercase
	 * @return the uppercase message, a space, the <code>Authentication</code> class that
	 * was on the <code>SecurityContext</code> at the time of method invocation, and a
	 * boolean indicating if the <code>Authentication</code> object is authenticated or
	 * not
	 */
	@Override
	public String makeUpperCase(String input) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return input.toUpperCase() + " " + auth.getClass().getName() + " " + auth.isAuthenticated();
	}

	/**
	 * Delegates through to the {@link #makeLowerCase(String)} method.
	 * @param input the message to be made lower-case
	 */
	@Override
	public String publicMakeLowerCase(String input) {
		return this.makeLowerCase(input);
	}

}
