package org.springframework.security.config.annotation.web.reactive;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.config.users.ReactiveAuthenticationTestConfiguration;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ServerHttpSecurityConfiguration}.
 *
 * @author Eleftheria Stein
 */
public class ServerHttpSecurityConfigurationTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Test
	public void loadConfigWhenReactiveUserDetailsServiceConfiguredThenServerHttpSecurityExists() {
		this.spring.register(ServerHttpSecurityConfiguration.class, ReactiveAuthenticationTestConfiguration.class,
				WebFluxSecurityConfiguration.class).autowire();
		ServerHttpSecurity serverHttpSecurity = this.spring.getContext().getBean(ServerHttpSecurity.class);
		assertThat(serverHttpSecurity).isNotNull();
	}

	@Test
	public void loadConfigWhenProxyingEnabledAndSubclassThenServerHttpSecurityExists() {
		this.spring.register(SubclassConfig.class, ReactiveAuthenticationTestConfiguration.class,
				WebFluxSecurityConfiguration.class).autowire();
		ServerHttpSecurity serverHttpSecurity = this.spring.getContext().getBean(ServerHttpSecurity.class);
		assertThat(serverHttpSecurity).isNotNull();
	}

	@Configuration
	static class SubclassConfig extends ServerHttpSecurityConfiguration {

	}

}
