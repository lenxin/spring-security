package org.springframework.security.concurrent;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests Explicitly specifying the {@link SecurityContext} on
 * {@link DelegatingSecurityContextExecutor}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class ExplicitDelegatingSecurityContextExecutorTests extends AbstractDelegatingSecurityContextExecutorTests {

	@Before
	public void setUp() throws Exception {
		super.explicitSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextExecutor create() {
		return new DelegatingSecurityContextExecutor(getExecutor(), this.securityContext);
	}

}
