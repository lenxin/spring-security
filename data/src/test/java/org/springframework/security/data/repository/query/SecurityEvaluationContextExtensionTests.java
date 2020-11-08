package org.springframework.security.data.repository.query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SecurityEvaluationContextExtensionTests {

	SecurityEvaluationContextExtension securityExtension;

	@Before
	public void setup() {
		this.securityExtension = new SecurityEvaluationContextExtension();
	}

	@After
	public void cleanup() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void getRootObjectSecurityContextHolderAuthenticationNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> getRoot().getAuthentication());
	}

	@Test
	public void getRootObjectSecurityContextHolderAuthentication() {
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		assertThat(getRoot().getAuthentication()).isSameAs(authentication);
	}

	@Test
	public void getRootObjectExplicitAuthenticationOverridesSecurityContextHolder() {
		TestingAuthenticationToken explicit = new TestingAuthenticationToken("explicit", "password", "ROLE_EXPLICIT");
		this.securityExtension = new SecurityEvaluationContextExtension(explicit);
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		assertThat(getRoot().getAuthentication()).isSameAs(explicit);
	}

	@Test
	public void getRootObjectExplicitAuthentication() {
		TestingAuthenticationToken explicit = new TestingAuthenticationToken("explicit", "password", "ROLE_EXPLICIT");
		this.securityExtension = new SecurityEvaluationContextExtension(explicit);
		assertThat(getRoot().getAuthentication()).isSameAs(explicit);
	}

	private SecurityExpressionRoot getRoot() {
		return this.securityExtension.getRootObject();
	}

}
