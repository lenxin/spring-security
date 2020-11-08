package org.springframework.security.authentication;

/**
 * An interface for resolving an {@link AuthenticationManager} based on the provided
 * context
 *
 * @author Josh Cummings
 * @since 5.2
 */
public interface AuthenticationManagerResolver<C> {

	/**
	 * Resolve an {@link AuthenticationManager} from a provided context
	 * @param context
	 * @return the {@link AuthenticationManager} to use
	 */
	AuthenticationManager resolve(C context);

}
