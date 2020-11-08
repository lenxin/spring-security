package org.springframework.security.config.annotation.authentication.configurers.userdetails;

import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Allows configuring a {@link UserDetailsService} within a
 * {@link AuthenticationManagerBuilder}.
 *
 * @param <B> the type of the {@link ProviderManagerBuilder}
 * @param <C> the {@link UserDetailsServiceConfigurer} (or this)
 * @param <U> the type of UserDetailsService being used to allow for returning the
 * concrete UserDetailsService.
 * @author Rob Winch
 * @since 3.2
 */
public class UserDetailsServiceConfigurer<B extends ProviderManagerBuilder<B>, C extends UserDetailsServiceConfigurer<B, C, U>, U extends UserDetailsService>
		extends AbstractDaoAuthenticationConfigurer<B, C, U> {

	/**
	 * Creates a new instance
	 * @param userDetailsService the {@link UserDetailsService} that should be used
	 */
	public UserDetailsServiceConfigurer(U userDetailsService) {
		super(userDetailsService);
	}

	@Override
	public void configure(B builder) throws Exception {
		initUserDetailsService();
		super.configure(builder);
	}

	/**
	 * Allows subclasses to initialize the {@link UserDetailsService}. For example, it
	 * might add users, initialize schema, etc.
	 */
	protected void initUserDetailsService() throws Exception {
	}

}
