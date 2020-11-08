package org.springframework.security.concurrent;

import org.junit.Before;

import org.springframework.security.core.context.SecurityContext;

/**
 * Tests using the current {@link SecurityContext} on
 * {@link DelegatingSecurityContextExecutor}
 *
 * @author Rob Winch
 * @since 3.2
 *
 */
public class CurrentDelegatingSecurityContextExecutorTests extends AbstractDelegatingSecurityContextExecutorTests {

	@Before
	public void setUp() throws Exception {
		super.currentSecurityContextPowermockSetup();
	}

	@Override
	protected DelegatingSecurityContextExecutor create() {
		return new DelegatingSecurityContextExecutor(getExecutor());
	}

}
