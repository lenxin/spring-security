package org.springframework.security.web.authentication.logout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
public class SecurityContextLogoutHandlerTests {

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private SecurityContextLogoutHandler handler;

	@Before
	public void setUp() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.handler = new SecurityContextLogoutHandler();
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(
				new TestingAuthenticationToken("user", "password", AuthorityUtils.createAuthorityList("ROLE_USER")));
		SecurityContextHolder.setContext(context);
	}

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	// SEC-2025
	@Test
	public void clearsAuthentication() {
		SecurityContext beforeContext = SecurityContextHolder.getContext();
		this.handler.logout(this.request, this.response, SecurityContextHolder.getContext().getAuthentication());
		assertThat(beforeContext.getAuthentication()).isNull();
	}

	@Test
	public void disableClearsAuthentication() {
		this.handler.setClearAuthentication(false);
		SecurityContext beforeContext = SecurityContextHolder.getContext();
		Authentication beforeAuthentication = beforeContext.getAuthentication();
		this.handler.logout(this.request, this.response, SecurityContextHolder.getContext().getAuthentication());
		assertThat(beforeContext.getAuthentication()).isNotNull();
		assertThat(beforeContext.getAuthentication()).isSameAs(beforeAuthentication);
	}

}
