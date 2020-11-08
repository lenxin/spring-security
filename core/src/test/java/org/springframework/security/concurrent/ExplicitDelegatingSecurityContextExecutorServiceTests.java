package org.springframework.security.concurrent;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests Explicitly specifying the {@link SecurityContext} on
 * {@link DelegatingSecurityContextExecutorService}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class ExplicitDelegatingSecurityContextExecutorServiceTests
		extends AbstractDelegatingSecurityContextExecutorServiceTests {

	@Before
	public void setUp() throws Exception {
		super.explicitSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextExecutorService create() {
		return new DelegatingSecurityContextExecutorService(this.delegate, this.securityContext);
	}

}
