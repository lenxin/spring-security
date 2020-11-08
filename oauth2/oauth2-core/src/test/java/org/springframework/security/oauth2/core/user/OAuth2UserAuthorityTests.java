package org.springframework.security.oauth2.core.user;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OAuth2UserAuthority}.
 *
 * @author Joe Grandja
 */
public class OAuth2UserAuthorityTests {

	private static final String AUTHORITY = "ROLE_USER";

	private static final Map<String, Object> ATTRIBUTES = Collections.singletonMap("username", "test");

	@Test
	public void constructorWhenAuthorityIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OAuth2UserAuthority(null, ATTRIBUTES));
	}

	@Test
	public void constructorWhenAttributesIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OAuth2UserAuthority(AUTHORITY, null));
	}

	@Test
	public void constructorWhenAttributesIsEmptyThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2UserAuthority(AUTHORITY, Collections.emptyMap()));
	}

	@Test
	public void constructorWhenAllParametersProvidedAndValidThenCreated() {
		OAuth2UserAuthority userAuthority = new OAuth2UserAuthority(AUTHORITY, ATTRIBUTES);
		assertThat(userAuthority.getAuthority()).isEqualTo(AUTHORITY);
		assertThat(userAuthority.getAttributes()).isEqualTo(ATTRIBUTES);
	}

}
