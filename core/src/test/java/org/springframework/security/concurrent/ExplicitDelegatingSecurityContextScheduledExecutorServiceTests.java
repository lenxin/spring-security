package org.springframework.security.concurrent;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests Explicitly specifying the {@link SecurityContext} on
 * {@link DelegatingSecurityContextScheduledExecutorService}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class ExplicitDelegatingSecurityContextScheduledExecutorServiceTests
		extends AbstractDelegatingSecurityContextScheduledExecutorServiceTests {

	@Before
	public void setUp() throws Exception {
		this.explicitSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextScheduledExecutorService create() {
		return new DelegatingSecurityContextScheduledExecutorService(this.delegate, this.securityContext);
	}

}
