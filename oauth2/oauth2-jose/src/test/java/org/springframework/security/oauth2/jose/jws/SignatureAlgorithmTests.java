package org.springframework.security.oauth2.jose.jws;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SignatureAlgorithm}
 *
 * @author Joe Grandja
 * @since 5.2
 */
public class SignatureAlgorithmTests {

	@Test
	public void fromWhenAlgorithmValidThenResolves() {
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.RS256)).isEqualTo(SignatureAlgorithm.RS256);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.RS384)).isEqualTo(SignatureAlgorithm.RS384);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.RS512)).isEqualTo(SignatureAlgorithm.RS512);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.ES256)).isEqualTo(SignatureAlgorithm.ES256);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.ES384)).isEqualTo(SignatureAlgorithm.ES384);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.ES512)).isEqualTo(SignatureAlgorithm.ES512);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.PS256)).isEqualTo(SignatureAlgorithm.PS256);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.PS384)).isEqualTo(SignatureAlgorithm.PS384);
		assertThat(SignatureAlgorithm.from(JwsAlgorithms.PS512)).isEqualTo(SignatureAlgorithm.PS512);
	}

	@Test
	public void fromWhenAlgorithmInvalidThenDoesNotResolve() {
		assertThat(SignatureAlgorithm.from("invalid")).isNull();
	}

}
