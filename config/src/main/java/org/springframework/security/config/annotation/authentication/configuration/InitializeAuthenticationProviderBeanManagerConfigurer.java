package org.springframework.security.config.annotation.authentication.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * Lazily initializes the global authentication with an {@link AuthenticationProvider} if
 * it is not yet configured and there is only a single Bean of that type.
 *
 * @author Rob Winch
 * @since 4.1
 */
@Order(InitializeAuthenticationProviderBeanManagerConfigurer.DEFAULT_ORDER)
class InitializeAuthenticationProviderBeanManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {

	static final int DEFAULT_ORDER = InitializeUserDetailsBeanManagerConfigurer.DEFAULT_ORDER - 100;

	private final ApplicationContext context;

	/**
	 * @param context the ApplicationContext to look up beans.
	 */
	InitializeAuthenticationProviderBeanManagerConfigurer(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.apply(new InitializeAuthenticationProviderManagerConfigurer());
	}

	class InitializeAuthenticationProviderManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {

		@Override
		public void configure(AuthenticationManagerBuilder auth) {
			if (auth.isConfigured()) {
				return;
			}
			AuthenticationProvider authenticationProvider = getBeanOrNull(AuthenticationProvider.class);
			if (authenticationProvider == null) {
				return;
			}
			auth.authenticationProvider(authenticationProvider);
		}

		/**
		 * @return a bean of the requested class if there's just a single registered
		 * component, null otherwise.
		 */
		private <T> T getBeanOrNull(Class<T> type) {
			String[] beanNames = InitializeAuthenticationProviderBeanManagerConfigurer.this.context
					.getBeanNamesForType(type);
			if (beanNames.length != 1) {
				return null;
			}
			return InitializeAuthenticationProviderBeanManagerConfigurer.this.context.getBean(beanNames[0], type);
		}

	}

}
