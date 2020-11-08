package org.springframework.security.integration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Luke Taylor
 * @since 2.0
 */
@ContextConfiguration(locations = { "/sec-936-app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SEC936ApplicationContextTests {

	/**
	 * SessionRegistry is used as the test service interface (nothing to do with the test)
	 */
	@Autowired
	private SessionRegistry sessionRegistry;

	@Test
	public void securityInterceptorHandlesCallWithNoTargetObject() {
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("bob", "bobspassword"));
		assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(this.sessionRegistry::getAllPrincipals);
	}

}
