package org.springframework.security.web.authentication.rememberme;

import org.junit.Test;

import org.springframework.security.web.authentication.NullRememberMeServices;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link org.springframework.security.web.authentication.NullRememberMeServices}.
 *
 * @author Ben Alex
 */
public class NullRememberMeServicesTests {

	@Test
	public void testAlwaysReturnsNull() {
		NullRememberMeServices services = new NullRememberMeServices();
		assertThat(services.autoLogin(null, null)).isNull();
		services.loginFail(null, null);
		services.loginSuccess(null, null, null);
	}

}
