package org.springframework.security.config.annotation.method.configuration;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.intercept.method.MockMethodInvocation;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.test.SpringTestRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class ReactiveMethodSecurityConfigurationTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler;

	@Test
	public void rolePrefixWithGrantedAuthorityDefaults() throws NoSuchMethodException {
		this.spring.register(WithRolePrefixConfiguration.class).autowire();
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("principal", "credential",
				"CUSTOM_ABC");
		MockMethodInvocation methodInvocation = new MockMethodInvocation(new Foo(), Foo.class, "bar", String.class);
		EvaluationContext context = this.methodSecurityExpressionHandler.createEvaluationContext(authentication,
				methodInvocation);
		SecurityExpressionRoot root = (SecurityExpressionRoot) context.getRootObject().getValue();
		assertThat(root.hasRole("ROLE_ABC")).isFalse();
		assertThat(root.hasRole("ROLE_CUSTOM_ABC")).isFalse();
		assertThat(root.hasRole("CUSTOM_ABC")).isTrue();
		assertThat(root.hasRole("ABC")).isTrue();
	}

	@Test
	public void rolePrefixWithDefaultConfig() throws NoSuchMethodException {
		this.spring.register(ReactiveMethodSecurityConfiguration.class).autowire();
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("principal", "credential",
				"ROLE_ABC");
		MockMethodInvocation methodInvocation = new MockMethodInvocation(new Foo(), Foo.class, "bar", String.class);
		EvaluationContext context = this.methodSecurityExpressionHandler.createEvaluationContext(authentication,
				methodInvocation);
		SecurityExpressionRoot root = (SecurityExpressionRoot) context.getRootObject().getValue();
		assertThat(root.hasRole("ROLE_ABC")).isTrue();
		assertThat(root.hasRole("ABC")).isTrue();
	}

	@Test
	public void rolePrefixWithGrantedAuthorityDefaultsAndSubclassWithProxyingEnabled() throws NoSuchMethodException {
		this.spring.register(SubclassConfig.class).autowire();
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("principal", "credential",
				"ROLE_ABC");
		MockMethodInvocation methodInvocation = new MockMethodInvocation(new Foo(), Foo.class, "bar", String.class);
		EvaluationContext context = this.methodSecurityExpressionHandler.createEvaluationContext(authentication,
				methodInvocation);
		SecurityExpressionRoot root = (SecurityExpressionRoot) context.getRootObject().getValue();
		assertThat(root.hasRole("ROLE_ABC")).isTrue();
		assertThat(root.hasRole("ABC")).isTrue();
	}

	@Configuration
	@EnableReactiveMethodSecurity // this imports ReactiveMethodSecurityConfiguration
	static class WithRolePrefixConfiguration {

		@Bean
		GrantedAuthorityDefaults grantedAuthorityDefaults() {
			return new GrantedAuthorityDefaults("CUSTOM_");
		}

	}

	@Configuration
	static class SubclassConfig extends ReactiveMethodSecurityConfiguration {

	}

	static class Foo {

		public void bar(String param) {
		}

	}

}
