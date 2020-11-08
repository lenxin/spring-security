package org.springframework.security.config.method;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.TestBusinessBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Luke Taylor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:org/springframework/security/config/method-security.xml")
public class InterceptMethodsBeanDefinitionDecoratorTests implements ApplicationContextAware {

	@Autowired
	@Qualifier("target")
	private TestBusinessBean target;

	@Autowired
	@Qualifier("transactionalTarget")
	private TestBusinessBean transactionalTarget;

	private ApplicationContext appContext;

	@BeforeClass
	public static void loadContext() {
		// Set value for placeholder
		System.setProperty("admin.role", "ROLE_ADMIN");
	}

	@After
	public void clearContext() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void targetDoesntLoseApplicationListenerInterface() {
		assertThat(this.appContext.getBeansOfType(ApplicationListener.class)).hasSize(1);
		assertThat(this.appContext.getBeanNamesForType(ApplicationListener.class)).hasSize(1);
		this.appContext.publishEvent(new AuthenticationSuccessEvent(new TestingAuthenticationToken("user", "")));
		assertThat(this.target).isInstanceOf(ApplicationListener.class);
	}

	@Test
	public void targetShouldAllowUnprotectedMethodInvocationWithNoContext() {
		this.target.unprotected();
	}

	@Test
	public void targetShouldPreventProtectedMethodInvocationWithNoContext() {
		assertThatExceptionOfType(AuthenticationCredentialsNotFoundException.class)
				.isThrownBy(this.target::doSomething);
	}

	@Test
	public void targetShouldAllowProtectedMethodInvocationWithCorrectRole() {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(token);
		this.target.doSomething();
	}

	@Test
	public void targetShouldPreventProtectedMethodInvocationWithIncorrectRole() {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_SOMEOTHERROLE"));
		SecurityContextHolder.getContext().setAuthentication(token);
		assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(this.target::doSomething);
	}

	@Test
	public void transactionalMethodsShouldBeSecured() {
		assertThatExceptionOfType(AuthenticationException.class).isThrownBy(this.transactionalTarget::doSomething);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

}
