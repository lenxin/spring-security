package org.springframework.security.config.annotation.authentication.configurers.userdetails;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Base class that allows access to the {@link UserDetailsService} for using as a default
 * value with {@link AuthenticationManagerBuilder}.
 *
 * @param <B> the type of the {@link ProviderManagerBuilder}
 * @param <U> the type of {@link UserDetailsService}
 * @author Rob Winch
 */
public abstract class UserDetailsAwareConfigurer<B extends ProviderManagerBuilder<B>, U extends UserDetailsService>
		extends SecurityConfigurerAdapter<AuthenticationManager, B> {

	/**
	 * Gets the {@link UserDetailsService} or null if it is not available
	 * @return the {@link UserDetailsService} or null if it is not available
	 */
	public abstract U getUserDetailsService();

}
