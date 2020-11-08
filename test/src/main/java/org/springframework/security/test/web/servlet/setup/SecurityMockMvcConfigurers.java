package org.springframework.security.test.web.servlet.setup;

import javax.servlet.Filter;

import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.util.Assert;

/**
 * Provides Security related
 * {@link org.springframework.test.web.servlet.setup.MockMvcConfigurer} implementations.
 *
 * @author Rob Winch
 * @since 4.0
 */
public final class SecurityMockMvcConfigurers {

	private SecurityMockMvcConfigurers() {
	}

	/**
	 * Configures the MockMvcBuilder for use with Spring Security. Specifically the
	 * configurer adds the Spring Bean named "springSecurityFilterChain" as a Filter. It
	 * will also ensure that the TestSecurityContextHolder is leveraged for each request
	 * by applying
	 * {@link org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors#testSecurityContext()}
	 * .
	 * @return the {@link org.springframework.test.web.servlet.setup.MockMvcConfigurer} to
	 * use
	 */
	public static MockMvcConfigurer springSecurity() {
		return new SecurityMockMvcConfigurer();
	}

	/**
	 * Configures the MockMvcBuilder for use with Spring Security. Specifically the
	 * configurer adds the provided Filter. It will also ensure that the
	 * TestSecurityContextHolder is leveraged for each request by applying
	 * {@link org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors#testSecurityContext()}
	 * .
	 * @param springSecurityFilterChain the Filter to be added
	 * @return the {@link org.springframework.test.web.servlet.setup.MockMvcConfigurer} to
	 * use
	 */
	public static MockMvcConfigurer springSecurity(Filter springSecurityFilterChain) {
		Assert.notNull(springSecurityFilterChain, "springSecurityFilterChain cannot be null");
		return new SecurityMockMvcConfigurer(springSecurityFilterChain);
	}

}
