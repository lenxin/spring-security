package org.springframework.security.concurrent;

import org.junit.Test;

import org.springframework.security.core.context.SecurityContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 3.2
 *
 */
public class DelegatingSecurityContextSupportTests extends AbstractDelegatingSecurityContextTestSupport {

	private AbstractDelegatingSecurityContextSupport support;

	@Test
	public void wrapCallable() throws Exception {
		explicitSecurityContextPowermockSetup();
		this.support = new ConcreteDelegatingSecurityContextSupport(this.securityContext);
		assertThat(this.support.wrap(this.callable)).isSameAs(this.wrappedCallable);
		assertThat(this.securityContextCaptor.getValue()).isSameAs(this.securityContext);
	}

	@Test
	public void wrapCallableNullSecurityContext() throws Exception {
		currentSecurityContextPowermockSetup();
		this.support = new ConcreteDelegatingSecurityContextSupport(null);
		assertThat(this.support.wrap(this.callable)).isSameAs(this.wrappedCallable);
	}

	@Test
	public void wrapRunnable() throws Exception {
		explicitSecurityContextPowermockSetup();
		this.support = new ConcreteDelegatingSecurityContextSupport(this.securityContext);
		assertThat(this.support.wrap(this.runnable)).isSameAs(this.wrappedRunnable);
		assertThat(this.securityContextCaptor.getValue()).isSameAs(this.securityContext);
	}

	@Test
	public void wrapRunnableNullSecurityContext() throws Exception {
		currentSecurityContextPowermockSetup();
		this.support = new ConcreteDelegatingSecurityContextSupport(null);
		assertThat(this.support.wrap(this.runnable)).isSameAs(this.wrappedRunnable);
	}

	private static class ConcreteDelegatingSecurityContextSupport extends AbstractDelegatingSecurityContextSupport {

		ConcreteDelegatingSecurityContextSupport(SecurityContext securityContext) {
			super(securityContext);
		}

	}

}
