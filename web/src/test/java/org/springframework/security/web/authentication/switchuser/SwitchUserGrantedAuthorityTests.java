package org.springframework.security.web.authentication.switchuser;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Clement Ng
 *
 */
public class SwitchUserGrantedAuthorityTests {

	@Test
	public void authorityWithNullRoleFailsAssertion() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SwitchUserGrantedAuthority(null, null))
				.withMessage("role cannot be null");
	}

	@Test
	public void authorityWithNullSourceFailsAssertion() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SwitchUserGrantedAuthority("role", null))
				.withMessage("source cannot be null");
	}

}
