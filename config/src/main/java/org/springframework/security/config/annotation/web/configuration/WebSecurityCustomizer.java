package org.springframework.security.config.annotation.web.configuration;

import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * Callback interface for customizing {@link WebSecurity}.
 *
 * Beans of this type will automatically be used by {@link WebSecurityConfiguration} to
 * customize {@link WebSecurity}.
 *
 * Example usage:
 *
 * <pre>
 * &#064;Bean
 * public WebSecurityCustomizer ignoringCustomizer() {
 * 	return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
 * }
 * </pre>
 *
 * @author Eleftheria Stein
 * @since 5.4
 */
@FunctionalInterface
public interface WebSecurityCustomizer {

	/**
	 * Performs the customizations on {@link WebSecurity}.
	 * @param web the instance of {@link WebSecurity} to apply to customizations to
	 */
	void customize(WebSecurity web);

}
