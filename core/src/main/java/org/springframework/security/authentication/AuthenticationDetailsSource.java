package org.springframework.security.authentication;

/**
 * Provides a {@link org.springframework.security.core.Authentication#getDetails()} object
 * for a given web request.
 *
 * @author Ben Alex
 */
public interface AuthenticationDetailsSource<C, T> {

	/**
	 * Called by a class when it wishes a new authentication details instance to be
	 * created.
	 * @param context the request object, which may be used by the authentication details
	 * object
	 * @return a fully-configured authentication details instance
	 */
	T buildDetails(C context);

}
