package org.springframework.security.config.method.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

/**
 * @author Rob Winch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class Gh4020GlobalMethodSecurityConfigurationTests {

	@Autowired
	DenyAllService denyAll;

	// gh-4020
	@Test
	public void denyAll() {
		assertThatExceptionOfType(AuthenticationCredentialsNotFoundException.class).isThrownBy(this.denyAll::denyAll);
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class SecurityConfig {

		@Bean
		PermissionEvaluator permissionEvaluator() {
			return mock(PermissionEvaluator.class);
		}

		@Bean
		RoleHierarchy RoleHierarchy() {
			return mock(RoleHierarchy.class);
		}

		@Bean
		AuthenticationTrustResolver trustResolver() {
			return mock(AuthenticationTrustResolver.class);
		}

		@Autowired
		DenyAllService denyAll;

	}

	@Configuration
	static class ServiceConfig {

		@Bean
		DenyAllService denyAllService() {
			return new DenyAllService();
		}

	}

	@PreAuthorize("denyAll")
	static class DenyAllService {

		void denyAll() {
		}

	}

}
