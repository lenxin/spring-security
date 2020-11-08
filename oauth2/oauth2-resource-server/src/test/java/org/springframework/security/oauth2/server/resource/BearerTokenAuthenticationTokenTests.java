package org.springframework.security.oauth2.server.resource;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link BearerTokenAuthenticationToken}
 *
 * @author Josh Cummings
 */
public class BearerTokenAuthenticationTokenTests {

	@Test
	public void constructorWhenTokenIsNullThenThrowsException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenAuthenticationToken(null))
				.withMessageContaining("token cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWhenTokenIsEmptyThenThrowsException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new BearerTokenAuthenticationToken(""))
				.withMessageContaining("token cannot be empty");
		// @formatter:on
	}

	@Test
	public void constructorWhenTokenHasValueThenConstructedCorrectly() {
		BearerTokenAuthenticationToken token = new BearerTokenAuthenticationToken("token");
		assertThat(token.getToken()).isEqualTo("token");
		assertThat(token.getPrincipal()).isEqualTo("token");
		assertThat(token.getCredentials()).isEqualTo("token");
	}

}
