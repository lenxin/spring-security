package org.springframework.security.oauth2.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link AuthorizationGrantType}.
 *
 * @author Joe Grandja
 */
public class AuthorizationGrantTypeTests {

	@Test
	public void constructorWhenValueIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AuthorizationGrantType(null));
	}

	@Test
	public void getValueWhenAuthorizationCodeGrantTypeThenReturnAuthorizationCode() {
		assertThat(AuthorizationGrantType.AUTHORIZATION_CODE.getValue()).isEqualTo("authorization_code");
	}

	@Test
	public void getValueWhenImplicitGrantTypeThenReturnImplicit() {
		assertThat(AuthorizationGrantType.IMPLICIT.getValue()).isEqualTo("implicit");
	}

	@Test
	public void getValueWhenRefreshTokenGrantTypeThenReturnRefreshToken() {
		assertThat(AuthorizationGrantType.REFRESH_TOKEN.getValue()).isEqualTo("refresh_token");
	}

	@Test
	public void getValueWhenPasswordGrantTypeThenReturnPassword() {
		assertThat(AuthorizationGrantType.PASSWORD.getValue()).isEqualTo("password");
	}

}
