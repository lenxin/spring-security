package org.springframework.security.oauth2.core.endpoint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OAuth2AuthorizationResponseType}.
 *
 * @author Joe Grandja
 */
public class OAuth2AuthorizationResponseTypeTests {

	@Test
	public void getValueWhenResponseTypeCodeThenReturnCode() {
		assertThat(OAuth2AuthorizationResponseType.CODE.getValue()).isEqualTo("code");
	}

	@Test
	public void getValueWhenResponseTypeTokenThenReturnToken() {
		assertThat(OAuth2AuthorizationResponseType.TOKEN.getValue()).isEqualTo("token");
	}

}
