package org.springframework.security.config.authentication;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Factory bean for the namespace AuthenticationManager, which allows a more meaningful
 * error message to be reported in the <tt>NoSuchBeanDefinitionException</tt>, if the user
 * has forgotten to declare the &lt;authentication-manager&gt; element.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public class AuthenticationManagerFactoryBean implements FactoryBean<AuthenticationManager>, BeanFactoryAware {

	private BeanFactory bf;

	public static final String MISSING_BEAN_ERROR_MESSAGE = "Did you forget to add a global <authentication-manager> element "
			+ "to your configuration (with child <authentication-provider> elements)? Alternatively you can use the "
			+ "authentication-manager-ref attribute on your <http> and <global-method-security> elements.";

	@Override
	public AuthenticationManager getObject() throws Exception {
		try {
			return (AuthenticationManager) this.bf.getBean(BeanIds.AUTHENTICATION_MANAGER);
		}
		catch (NoSuchBeanDefinitionException ex) {
			if (!BeanIds.AUTHENTICATION_MANAGER.equals(ex.getBeanName())) {
				throw ex;
			}
			UserDetailsService uds = getBeanOrNull(UserDetailsService.class);
			if (uds == null) {
				throw new NoSuchBeanDefinitionException(BeanIds.AUTHENTICATION_MANAGER, MISSING_BEAN_ERROR_MESSAGE);
			}
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(uds);
			PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);
			if (passwordEncoder != null) {
				provider.setPasswordEncoder(passwordEncoder);
			}
			provider.afterPropertiesSet();
			return new ProviderManager(Arrays.<AuthenticationProvider>asList(provider));
		}
	}

	@Override
	public Class<? extends AuthenticationManager> getObjectType() {
		return ProviderManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.bf = beanFactory;
	}

	private <T> T getBeanOrNull(Class<T> type) {
		try {
			return this.bf.getBean(type);
		}
		catch (NoSuchBeanDefinitionException noUds) {
			return null;
		}
	}

}
