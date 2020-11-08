package org.springframework.security.acls.domain;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AclAuthorizationStrategyImplTests {

	@Mock
	Acl acl;

	GrantedAuthority authority;

	AclAuthorizationStrategyImpl strategy;

	@Before
	public void setup() {
		this.authority = new SimpleGrantedAuthority("ROLE_AUTH");
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("foo", "bar",
				Arrays.asList(this.authority));
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@After
	public void cleanup() {
		SecurityContextHolder.clearContext();
	}

	// gh-4085
	@Test
	public void securityCheckWhenCustomAuthorityThenNameIsUsed() {
		this.strategy = new AclAuthorizationStrategyImpl(new CustomAuthority());
		this.strategy.securityCheck(this.acl, AclAuthorizationStrategy.CHANGE_GENERAL);
	}

	@SuppressWarnings("serial")
	class CustomAuthority implements GrantedAuthority {

		@Override
		public String getAuthority() {
			return AclAuthorizationStrategyImplTests.this.authority.getAuthority();
		}

	}

}
