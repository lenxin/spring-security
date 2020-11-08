package org.springframework.security.config.annotation.web.configurers;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * Allows persisting and restoring of the {@link SecurityContext} found on the
 * {@link SecurityContextHolder} for each request by configuring the
 * {@link SecurityContextPersistenceFilter}. All properties have reasonable defaults, so
 * no additional configuration is required other than applying this
 * {@link org.springframework.security.config.annotation.SecurityConfigurer}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link SecurityContextPersistenceFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>If {@link SessionManagementConfigurer}, is provided and set to always, then the
 * {@link SecurityContextPersistenceFilter#setForceEagerSessionCreation(boolean)} will be
 * set to true.</li>
 * <li>{@link SecurityContextRepository} must be set and is used on
 * {@link SecurityContextPersistenceFilter}.</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class SecurityContextConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<SecurityContextConfigurer<H>, H> {

	/**
	 * Creates a new instance
	 * @see HttpSecurity#securityContext()
	 */
	public SecurityContextConfigurer() {
	}

	/**
	 * Specifies the shared {@link SecurityContextRepository} that is to be used
	 * @param securityContextRepository the {@link SecurityContextRepository} to use
	 * @return the {@link HttpSecurity} for further customizations
	 */
	public SecurityContextConfigurer<H> securityContextRepository(SecurityContextRepository securityContextRepository) {
		getBuilder().setSharedObject(SecurityContextRepository.class, securityContextRepository);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void configure(H http) {
		SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
		if (securityContextRepository == null) {
			securityContextRepository = new HttpSessionSecurityContextRepository();
		}
		SecurityContextPersistenceFilter securityContextFilter = new SecurityContextPersistenceFilter(
				securityContextRepository);
		SessionManagementConfigurer<?> sessionManagement = http.getConfigurer(SessionManagementConfigurer.class);
		SessionCreationPolicy sessionCreationPolicy = (sessionManagement != null)
				? sessionManagement.getSessionCreationPolicy() : null;
		if (SessionCreationPolicy.ALWAYS == sessionCreationPolicy) {
			securityContextFilter.setForceEagerSessionCreation(true);
		}
		securityContextFilter = postProcess(securityContextFilter);
		http.addFilter(securityContextFilter);
	}

}
