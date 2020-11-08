package org.springframework.security.config.annotation.method.configuration;

import org.springframework.context.annotation.Bean;

/**
 * @author Josh Cummings
 */
public class MethodSecurityServiceConfig {

	@Bean
	MethodSecurityService service() {
		return new MethodSecurityServiceImpl();
	}

}
