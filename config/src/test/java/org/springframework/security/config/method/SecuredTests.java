package org.springframework.security.config.method;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Rob Winch
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SecuredTests {

	@Autowired
	SecuredServiceImpl service;

	@After
	public void cleanup() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void securedAdminRoleDenied() {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken("user", "pass", "ROLE_USER"));
		assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(this.service::securedAdminRole);
	}

	@Test
	public void securedAdminRoleGranted() {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken("user", "pass", "ROLE_ADMIN"));
		this.service.securedAdminRole();
	}

}
