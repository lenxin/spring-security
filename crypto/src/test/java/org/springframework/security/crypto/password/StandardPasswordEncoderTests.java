package org.springframework.security.crypto.password;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("deprecation")
public class StandardPasswordEncoderTests {

	private StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret");

	@Test
	public void matches() {
		String result = this.encoder.encode("password");
		assertThat(result).isNotEqualTo("password");
		assertThat(this.encoder.matches("password", result)).isTrue();
	}

	@Test
	public void matchesLengthChecked() {
		String result = this.encoder.encode("password");
		assertThat(this.encoder.matches("password", result.substring(0, result.length() - 2))).isFalse();
	}

	@Test
	public void notMatches() {
		String result = this.encoder.encode("password");
		assertThat(this.encoder.matches("bogus", result)).isFalse();
	}

}
