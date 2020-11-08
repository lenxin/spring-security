package org.springframework.security.authentication.jaas;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Clement Ng
 *
 */
public class JaasGrantedAuthorityTests {

	@Test
	public void authorityWithNullRoleFailsAssertion() {
		assertThatIllegalArgumentException().isThrownBy(() -> new JaasGrantedAuthority(null, null))
				.withMessageContaining("role cannot be null");
	}

	@Test
	public void authorityWithNullPrincipleFailsAssertion() {
		assertThatIllegalArgumentException().isThrownBy(() -> new JaasGrantedAuthority("role", null))
				.withMessageContaining("principal cannot be null");
	}

}
