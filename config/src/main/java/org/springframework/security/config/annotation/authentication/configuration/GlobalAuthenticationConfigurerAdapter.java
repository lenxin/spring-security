package org.springframework.security.config.annotation.authentication.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * A {@link SecurityConfigurer} that can be exposed as a bean to configure the global
 * {@link AuthenticationManagerBuilder}. Beans of this type are automatically used by
 * {@link AuthenticationConfiguration} to configure the global
 * {@link AuthenticationManagerBuilder}.
 *
 * @since 5.0
 * @author Rob Winch
 */
@Order(100)
public abstract class GlobalAuthenticationConfigurerAdapter
		implements SecurityConfigurer<AuthenticationManager, AuthenticationManagerBuilder> {

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	}

}
