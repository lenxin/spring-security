package org.springframework.security.config.annotation.web.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.util.Assert;

/**
 * A class used to get all the {@link WebSecurityConfigurer} instances from the current
 * {@link ApplicationContext} but ignoring the parent.
 *
 * @author Rob Winch
 */
public final class AutowiredWebSecurityConfigurersIgnoreParents {

	private final ConfigurableListableBeanFactory beanFactory;

	AutowiredWebSecurityConfigurersIgnoreParents(ConfigurableListableBeanFactory beanFactory) {
		Assert.notNull(beanFactory, "beanFactory cannot be null");
		this.beanFactory = beanFactory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecurityConfigurer<Filter, WebSecurity>> getWebSecurityConfigurers() {
		List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers = new ArrayList<>();
		Map<String, WebSecurityConfigurer> beansOfType = this.beanFactory.getBeansOfType(WebSecurityConfigurer.class);
		for (Entry<String, WebSecurityConfigurer> entry : beansOfType.entrySet()) {
			webSecurityConfigurers.add(entry.getValue());
		}
		return webSecurityConfigurers;
	}

}
