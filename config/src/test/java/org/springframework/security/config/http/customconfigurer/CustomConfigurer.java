package org.springframework.security.config.http.customconfigurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author Rob Winch
 *
 */
public class CustomConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Value("${permitAllPattern}")
	private String permitAllPattern;

	private String loginPage = "/login";

	@SuppressWarnings("unchecked")
	@Override
	public void init(HttpSecurity http) throws Exception {
		// autowire this bean
		ApplicationContext context = http.getSharedObject(ApplicationContext.class);
		context.getAutowireCapableBeanFactory().autowireBean(this);
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers(this.permitAllPattern).permitAll()
				.anyRequest().authenticated();
		// @formatter:on
		if (http.getConfigurer(FormLoginConfigurer.class) == null) {
			// only apply if formLogin() was not invoked by the user
			// @formatter:off
			http
				.formLogin()
					.loginPage(this.loginPage);
			// @formatter:on
		}
	}

	public CustomConfigurer loginPage(String loginPage) {
		this.loginPage = loginPage;
		return this;
	}

	public static CustomConfigurer customConfigurer() {
		return new CustomConfigurer();
	}

}
