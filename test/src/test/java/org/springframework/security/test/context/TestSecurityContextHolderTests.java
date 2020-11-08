package org.springframework.security.test.context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TestSecurityContextHolderTests {

	private SecurityContext context;

	@Before
	public void setup() {
		this.context = SecurityContextHolder.createEmptyContext();
	}

	@After
	public void cleanup() {
		TestSecurityContextHolder.clearContext();
	}

	@Test
	public void clearContextClearsBoth() {
		SecurityContextHolder.setContext(this.context);
		TestSecurityContextHolder.setContext(this.context);
		TestSecurityContextHolder.clearContext();
		assertThat(SecurityContextHolder.getContext()).isNotSameAs(this.context);
		assertThat(TestSecurityContextHolder.getContext()).isNotSameAs(this.context);
	}

	@Test
	public void getContextDefaultsNonNull() {
		assertThat(TestSecurityContextHolder.getContext()).isNotNull();
		assertThat(SecurityContextHolder.getContext()).isNotNull();
	}

	@Test
	public void setContextSetsBoth() {
		TestSecurityContextHolder.setContext(this.context);
		assertThat(TestSecurityContextHolder.getContext()).isSameAs(this.context);
		assertThat(SecurityContextHolder.getContext()).isSameAs(this.context);
	}

	@Test
	public void setContextWithAuthentication() {
		Authentication authentication = mock(Authentication.class);
		TestSecurityContextHolder.setAuthentication(authentication);
		assertThat(TestSecurityContextHolder.getContext().getAuthentication()).isSameAs(authentication);
	}

}
