package org.springframework.security.config.annotation.web.reactive;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.config.users.ReactiveAuthenticationTestConfiguration;
import org.springframework.security.web.server.WebFilterChainProxy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebFluxSecurityConfiguration}.
 *
 * @author Eleftheria Stein
 */
public class WebFluxSecurityConfigurationTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Test
	public void loadConfigWhenReactiveUserDetailsServiceConfiguredThenWebFilterChainProxyExists() {
		this.spring.register(ServerHttpSecurityConfiguration.class, ReactiveAuthenticationTestConfiguration.class,
				WebFluxSecurityConfiguration.class).autowire();
		WebFilterChainProxy webFilterChainProxy = this.spring.getContext().getBean(WebFilterChainProxy.class);
		assertThat(webFilterChainProxy).isNotNull();
	}

	@Test
	public void loadConfigWhenBeanProxyingEnabledAndSubclassThenWebFilterChainProxyExists() {
		this.spring.register(ServerHttpSecurityConfiguration.class, ReactiveAuthenticationTestConfiguration.class,
				WebFluxSecurityConfigurationTests.SubclassConfig.class).autowire();
		WebFilterChainProxy webFilterChainProxy = this.spring.getContext().getBean(WebFilterChainProxy.class);
		assertThat(webFilterChainProxy).isNotNull();
	}

	@Configuration
	static class SubclassConfig extends WebFluxSecurityConfiguration {

	}

}
