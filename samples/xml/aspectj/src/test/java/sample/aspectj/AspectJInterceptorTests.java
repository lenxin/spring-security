package sample.aspectj;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:aspectj-context.xml")
public class AspectJInterceptorTests {
	private Authentication admin = new UsernamePasswordAuthenticationToken("test", "xxx",
			AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
	private Authentication user = new UsernamePasswordAuthenticationToken("test", "xxx",
			AuthorityUtils.createAuthorityList("ROLE_USER"));

	@Autowired
	private Service service;

	@Autowired
	private SecuredService securedService;

	@Test
	public void testPublicMethod() {
		service.publicMethod();
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testSecuredMethodNotAuthenticated() {
		service.secureMethod();
	}

	@Test(expected = AccessDeniedException.class)
	public void testSecuredMethodWrongRole() {
		SecurityContextHolder.getContext().setAuthentication(admin);
		service.secureMethod();
	}

	@Test
	public void testSecuredMethodEverythingOk() {
		SecurityContextHolder.getContext().setAuthentication(user);
		service.secureMethod();
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testSecuredClassNotAuthenticated() {
		securedService.secureMethod();
	}

	@Test(expected = AccessDeniedException.class)
	public void testSecuredClassWrongRole() {
		SecurityContextHolder.getContext().setAuthentication(admin);
		securedService.secureMethod();
	}

	@Test(expected = AccessDeniedException.class)
	public void testSecuredClassWrongRoleOnNewedInstance() {
		SecurityContextHolder.getContext().setAuthentication(admin);
		new SecuredService().secureMethod();
	}

	@Test
	public void testSecuredClassEverythingOk() {
		SecurityContextHolder.getContext().setAuthentication(user);
		securedService.secureMethod();
		new SecuredService().secureMethod();
	}

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

}
