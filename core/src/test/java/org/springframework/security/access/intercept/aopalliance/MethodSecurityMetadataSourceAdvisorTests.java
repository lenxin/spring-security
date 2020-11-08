package org.springframework.security.access.intercept.aopalliance;

import java.lang.reflect.Method;

import org.junit.Test;

import org.springframework.security.TargetObject;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link MethodSecurityMetadataSourceAdvisor}.
 *
 * @author Ben Alex
 */
public class MethodSecurityMetadataSourceAdvisorTests {

	@Test
	public void testAdvisorReturnsFalseWhenMethodInvocationNotDefined() throws Exception {
		Class<TargetObject> clazz = TargetObject.class;
		Method method = clazz.getMethod("makeLowerCase", new Class[] { String.class });
		MethodSecurityMetadataSource mds = mock(MethodSecurityMetadataSource.class);
		given(mds.getAttributes(method, clazz)).willReturn(null);
		MethodSecurityMetadataSourceAdvisor advisor = new MethodSecurityMetadataSourceAdvisor("", mds, "");
		assertThat(advisor.getPointcut().getMethodMatcher().matches(method, clazz)).isFalse();
	}

	@Test
	public void testAdvisorReturnsTrueWhenMethodInvocationIsDefined() throws Exception {
		Class<TargetObject> clazz = TargetObject.class;
		Method method = clazz.getMethod("countLength", new Class[] { String.class });
		MethodSecurityMetadataSource mds = mock(MethodSecurityMetadataSource.class);
		given(mds.getAttributes(method, clazz)).willReturn(SecurityConfig.createList("ROLE_A"));
		MethodSecurityMetadataSourceAdvisor advisor = new MethodSecurityMetadataSourceAdvisor("", mds, "");
		assertThat(advisor.getPointcut().getMethodMatcher().matches(method, clazz)).isTrue();
	}

}
