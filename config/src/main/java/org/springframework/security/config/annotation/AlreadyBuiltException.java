package org.springframework.security.config.annotation;

/**
 * Thrown when {@link AbstractSecurityBuilder#build()} is two or more times.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class AlreadyBuiltException extends IllegalStateException {

	public AlreadyBuiltException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -5891004752785553015L;

}
