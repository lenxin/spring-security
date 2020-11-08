package org.springframework.security.config.annotation.authentication.configurers.userdetails;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Allows configuring a {@link DaoAuthenticationProvider}
 *
 * @param <B> The type of {@link ProviderManagerBuilder} this is
 * @param <U> The type of {@link UserDetailsService} that is being used
 * @author Rob Winch
 * @since 3.2
 */
public class DaoAuthenticationConfigurer<B extends ProviderManagerBuilder<B>, U extends UserDetailsService>
		extends AbstractDaoAuthenticationConfigurer<B, DaoAuthenticationConfigurer<B, U>, U> {

	/**
	 * Creates a new instance
	 * @param userDetailsService
	 */
	public DaoAuthenticationConfigurer(U userDetailsService) {
		super(userDetailsService);
	}

}
