package org.springframework.security.concurrent;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests using the current {@link SecurityContext} on
 * {@link DelegatingSecurityContextScheduledExecutorService}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class CurrentDelegatingSecurityContextScheduledExecutorServiceTests
		extends AbstractDelegatingSecurityContextScheduledExecutorServiceTests {

	@Before
	public void setUp() throws Exception {
		this.currentSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextScheduledExecutorService create() {
		return new DelegatingSecurityContextScheduledExecutorService(this.delegate);
	}

}
