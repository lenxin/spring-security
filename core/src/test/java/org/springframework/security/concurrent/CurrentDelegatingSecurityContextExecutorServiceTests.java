package org.springframework.security.concurrent;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests using the current {@link SecurityContext} on
 * {@link DelegatingSecurityContextExecutorService}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class CurrentDelegatingSecurityContextExecutorServiceTests
		extends AbstractDelegatingSecurityContextExecutorServiceTests {

	@Before
	public void setUp() throws Exception {
		super.currentSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextExecutorService create() {
		return new DelegatingSecurityContextExecutorService(this.delegate);
	}

}
