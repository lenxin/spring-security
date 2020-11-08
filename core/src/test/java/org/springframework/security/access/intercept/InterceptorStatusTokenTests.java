package org.springframework.security.access.intercept;

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link InterceptorStatusToken}.
 *
 * @author Ben Alex
 */
public class InterceptorStatusTokenTests {

	@Test
	public void testOperation() {
		List<ConfigAttribute> attr = SecurityConfig.createList("FOO");
		MethodInvocation mi = new SimpleMethodInvocation();
		SecurityContext ctx = SecurityContextHolder.createEmptyContext();
		InterceptorStatusToken token = new InterceptorStatusToken(ctx, true, attr, mi);
		assertThat(token.isContextHolderRefreshRequired()).isTrue();
		assertThat(token.getAttributes()).isEqualTo(attr);
		assertThat(token.getSecureObject()).isEqualTo(mi);
		assertThat(token.getSecurityContext()).isSameAs(ctx);
	}

}
