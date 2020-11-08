package org.springframework.security.crypto.password;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("deprecation")
public class Md4PasswordEncoderTests {

	@Test
	public void testEncodeUnsaltedPassword() {
		Md4PasswordEncoder md4 = new Md4PasswordEncoder();
		md4.setEncodeHashAsBase64(true);
		assertThat(md4.matches("ww_uni123", "8zobtq72iAt0W6KNqavGwg==")).isTrue();
	}

	@Test
	public void testEncodeSaltedPassword() {
		Md4PasswordEncoder md4 = new Md4PasswordEncoder();
		md4.setEncodeHashAsBase64(true);
		assertThat(md4.matches("ww_uni123", "{Alan K Stewart}ZplT6P5Kv6Rlu6W4FIoYNA==")).isTrue();
	}

	@Test
	public void testEncodeNullPassword() {
		Md4PasswordEncoder md4 = new Md4PasswordEncoder();
		md4.setEncodeHashAsBase64(true);
		assertThat(md4.matches(null, "MdbP4NFq6TG3PFnX4MCJwA==")).isTrue();
	}

	@Test
	public void testEncodeEmptyPassword() {
		Md4PasswordEncoder md4 = new Md4PasswordEncoder();
		md4.setEncodeHashAsBase64(true);
		assertThat(md4.matches(null, "MdbP4NFq6TG3PFnX4MCJwA==")).isTrue();
	}

	@Test
	public void testNonAsciiPasswordHasCorrectHash() {
		Md4PasswordEncoder md4 = new Md4PasswordEncoder();
		assertThat(md4.matches("\u4F60\u597d", "a7f1196539fd1f85f754ffd185b16e6e")).isTrue();
	}

	@Test
	public void testEncodedMatches() {
		String rawPassword = "password";
		Md4PasswordEncoder md4 = new Md4PasswordEncoder();
		String encodedPassword = md4.encode(rawPassword);
		assertThat(md4.matches(rawPassword, encodedPassword)).isTrue();
	}

	@Test
	public void javadocWhenHasSaltThenMatches() {
		Md4PasswordEncoder encoder = new Md4PasswordEncoder();
		assertThat(encoder.matches("password", "{thisissalt}6cc7924dad12ade79dfb99e424f25260"));
	}

}
