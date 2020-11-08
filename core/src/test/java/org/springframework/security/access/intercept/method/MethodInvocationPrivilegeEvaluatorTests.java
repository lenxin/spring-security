package org.springframework.security.access.intercept.method;

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import org.springframework.security.ITargetObject;
import org.springframework.security.OtherTargetObject;
import org.springframework.security.TargetObject;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.intercept.MethodInvocationPrivilegeEvaluator;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.MethodInvocationUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

/**
 * Tests
 * {@link org.springframework.security.access.intercept.MethodInvocationPrivilegeEvaluator}
 * .
 *
 * @author Ben Alex
 */
public class MethodInvocationPrivilegeEvaluatorTests {

	private TestingAuthenticationToken token;

	private MethodSecurityInterceptor interceptor;

	private AccessDecisionManager adm;

	private MethodSecurityMetadataSource mds;

	private final List<ConfigAttribute> role = SecurityConfig.createList("ROLE_IGNORED");

	@Before
	public final void setUp() {
		SecurityContextHolder.clearContext();
		this.interceptor = new MethodSecurityInterceptor();
		this.token = new TestingAuthenticationToken("Test", "Password", "ROLE_SOMETHING");
		this.adm = mock(AccessDecisionManager.class);
		AuthenticationManager authman = mock(AuthenticationManager.class);
		this.mds = mock(MethodSecurityMetadataSource.class);
		this.interceptor.setAccessDecisionManager(this.adm);
		this.interceptor.setAuthenticationManager(authman);
		this.interceptor.setSecurityMetadataSource(this.mds);
	}

	@Test
	public void allowsAccessUsingCreate() throws Exception {
		Object object = new TargetObject();
		final MethodInvocation mi = MethodInvocationUtils.create(object, "makeLowerCase", "foobar");
		MethodInvocationPrivilegeEvaluator mipe = new MethodInvocationPrivilegeEvaluator();
		given(this.mds.getAttributes(mi)).willReturn(this.role);
		mipe.setSecurityInterceptor(this.interceptor);
		mipe.afterPropertiesSet();
		assertThat(mipe.isAllowed(mi, this.token)).isTrue();
	}

	@Test
	public void allowsAccessUsingCreateFromClass() {
		final MethodInvocation mi = MethodInvocationUtils.createFromClass(new OtherTargetObject(), ITargetObject.class,
				"makeLowerCase", new Class[] { String.class }, new Object[] { "Hello world" });
		MethodInvocationPrivilegeEvaluator mipe = new MethodInvocationPrivilegeEvaluator();
		mipe.setSecurityInterceptor(this.interceptor);
		given(this.mds.getAttributes(mi)).willReturn(this.role);
		assertThat(mipe.isAllowed(mi, this.token)).isTrue();
	}

	@Test
	public void declinesAccessUsingCreate() {
		Object object = new TargetObject();
		final MethodInvocation mi = MethodInvocationUtils.create(object, "makeLowerCase", "foobar");
		MethodInvocationPrivilegeEvaluator mipe = new MethodInvocationPrivilegeEvaluator();
		mipe.setSecurityInterceptor(this.interceptor);
		given(this.mds.getAttributes(mi)).willReturn(this.role);
		willThrow(new AccessDeniedException("rejected")).given(this.adm).decide(this.token, mi, this.role);
		assertThat(mipe.isAllowed(mi, this.token)).isFalse();
	}

	@Test
	public void declinesAccessUsingCreateFromClass() {
		final MethodInvocation mi = MethodInvocationUtils.createFromClass(new OtherTargetObject(), ITargetObject.class,
				"makeLowerCase", new Class[] { String.class }, new Object[] { "helloWorld" });
		MethodInvocationPrivilegeEvaluator mipe = new MethodInvocationPrivilegeEvaluator();
		mipe.setSecurityInterceptor(this.interceptor);
		given(this.mds.getAttributes(mi)).willReturn(this.role);
		willThrow(new AccessDeniedException("rejected")).given(this.adm).decide(this.token, mi, this.role);
		assertThat(mipe.isAllowed(mi, this.token)).isFalse();
	}

}
