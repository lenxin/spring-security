package org.springframework.security;

/**
 * Represents the interface of a secured object.
 *
 * @author Ben Alex
 */
public interface ITargetObject {

	Integer computeHashCode(String input);

	int countLength(String input);

	String makeLowerCase(String input);

	String makeUpperCase(String input);

	String publicMakeLowerCase(String input);

}
