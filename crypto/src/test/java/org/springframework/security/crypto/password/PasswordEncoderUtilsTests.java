package org.springframework.security.crypto.password;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
public class PasswordEncoderUtilsTests {

	@Test
	public void equalsWhenDifferentLengthThenFalse() {
		assertThat(PasswordEncoderUtils.equals("abc", "a")).isFalse();
		assertThat(PasswordEncoderUtils.equals("a", "abc")).isFalse();
	}

	@Test
	public void equalsWhenNullAndNotEmtpyThenFalse() {
		assertThat(PasswordEncoderUtils.equals(null, "a")).isFalse();
		assertThat(PasswordEncoderUtils.equals("a", null)).isFalse();
	}

	@Test
	public void equalsWhenNullAndNullThenTrue() {
		assertThat(PasswordEncoderUtils.equals(null, null)).isTrue();
	}

	@Test
	public void equalsWhenNullAndEmptyThenFalse() {
		assertThat(PasswordEncoderUtils.equals(null, "")).isFalse();
		assertThat(PasswordEncoderUtils.equals("", null)).isFalse();
	}

	@Test
	public void equalsWhenNotEmptyAndEmptyThenFalse() {
		assertThat(PasswordEncoderUtils.equals("abc", "")).isFalse();
		assertThat(PasswordEncoderUtils.equals("", "abc")).isFalse();
	}

	@Test
	public void equalsWhenEmtpyAndEmptyThenTrue() {
		assertThat(PasswordEncoderUtils.equals("", "")).isTrue();
	}

	@Test
	public void equalsWhenDifferentCaseThenFalse() {
		assertThat(PasswordEncoderUtils.equals("aBc", "abc")).isFalse();
	}

	@Test
	public void equalsWhenSameThenTrue() {
		assertThat(PasswordEncoderUtils.equals("abcdef", "abcdef")).isTrue();
	}

}
