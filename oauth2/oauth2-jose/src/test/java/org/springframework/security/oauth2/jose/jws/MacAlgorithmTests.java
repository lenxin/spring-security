package org.springframework.security.oauth2.jose.jws;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MacAlgorithm}
 *
 * @author Joe Grandja
 * @since 5.2
 */
public class MacAlgorithmTests {

	@Test
	public void fromWhenAlgorithmValidThenResolves() {
		assertThat(MacAlgorithm.from(JwsAlgorithms.HS256)).isEqualTo(MacAlgorithm.HS256);
		assertThat(MacAlgorithm.from(JwsAlgorithms.HS384)).isEqualTo(MacAlgorithm.HS384);
		assertThat(MacAlgorithm.from(JwsAlgorithms.HS512)).isEqualTo(MacAlgorithm.HS512);
	}

	@Test
	public void fromWhenAlgorithmInvalidThenDoesNotResolve() {
		assertThat(MacAlgorithm.from("invalid")).isNull();
	}

}
