package org.springframework.security.access;

import java.util.List;

import org.junit.Test;

import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.util.SimpleMethodInvocation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link AuthorizationFailureEvent}.
 *
 * @author Ben Alex
 */
public class AuthorizationFailureEventTests {

	private final UsernamePasswordAuthenticationToken foo = new UsernamePasswordAuthenticationToken("foo", "bar");

	private List<ConfigAttribute> attributes = SecurityConfig.createList("TEST");

	private AccessDeniedException exception = new AuthorizationServiceException("error", new Throwable());

	@Test
	public void rejectsNullSecureObject() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AuthorizationFailureEvent(null, this.attributes, this.foo, this.exception));
	}

	@Test
	public void rejectsNullAttributesList() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new AuthorizationFailureEvent(new SimpleMethodInvocation(), null, this.foo, this.exception));
	}

	@Test
	public void rejectsNullAuthentication() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AuthorizationFailureEvent(new SimpleMethodInvocation(), this.attributes, null,
						this.exception));
	}

	@Test
	public void rejectsNullException() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new AuthorizationFailureEvent(new SimpleMethodInvocation(), this.attributes, this.foo, null));
	}

	@Test
	public void gettersReturnCtorSuppliedData() {
		AuthorizationFailureEvent event = new AuthorizationFailureEvent(new Object(), this.attributes, this.foo,
				this.exception);
		assertThat(event.getConfigAttributes()).isSameAs(this.attributes);
		assertThat(event.getAccessDeniedException()).isSameAs(this.exception);
		assertThat(event.getAuthentication()).isSameAs(this.foo);
	}

}
