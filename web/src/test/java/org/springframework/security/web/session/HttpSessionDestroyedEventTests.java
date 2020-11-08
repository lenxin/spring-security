package org.springframework.security.web.session;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Rob Winch
 *
 */
public class HttpSessionDestroyedEventTests {

	private MockHttpSession session;

	private HttpSessionDestroyedEvent destroyedEvent;

	@Before
	public void setUp() {
		this.session = new MockHttpSession();
		this.session.setAttribute("notcontext", "notcontext");
		this.session.setAttribute("null", null);
		this.session.setAttribute("context", new SecurityContextImpl());
		this.destroyedEvent = new HttpSessionDestroyedEvent(this.session);
	}

	// SEC-1870
	@Test
	public void getSecurityContexts() {
		List<SecurityContext> securityContexts = this.destroyedEvent.getSecurityContexts();
		assertThat(securityContexts).hasSize(1);
		assertThat(securityContexts.get(0)).isSameAs(this.session.getAttribute("context"));
	}

	@Test
	public void getSecurityContextsMulti() {
		this.session.setAttribute("another", new SecurityContextImpl());
		List<SecurityContext> securityContexts = this.destroyedEvent.getSecurityContexts();
		assertThat(securityContexts).hasSize(2);
	}

	@Test
	public void getSecurityContextsDiffImpl() {
		this.session.setAttribute("context", mock(SecurityContext.class));
		List<SecurityContext> securityContexts = this.destroyedEvent.getSecurityContexts();
		assertThat(securityContexts).hasSize(1);
		assertThat(securityContexts.get(0)).isSameAs(this.session.getAttribute("context"));
	}

}
