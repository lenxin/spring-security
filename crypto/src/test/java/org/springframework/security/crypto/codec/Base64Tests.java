package org.springframework.security.crypto.codec;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Luke Taylor
 */
@SuppressWarnings("deprecation")
public class Base64Tests {

	@Test
	public void isBase64ReturnsTrueForValidBase64() {
		assertThat(Base64.isBase64(new byte[] { (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D' })).isTrue();
	}

	@Test
	public void isBase64ReturnsFalseForInvalidBase64() {
		// Include invalid '`' character
		assertThat(Base64.isBase64(new byte[] { (byte) 'A', (byte) 'B', (byte) 'C', (byte) '`' })).isFalse();
	}

	@Test
	public void isBase64RejectsNull() {
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Base64.isBase64(null));
	}

	@Test
	public void isBase64RejectsInvalidLength() {
		assertThatIllegalArgumentException().isThrownBy(() -> Base64.isBase64(new byte[] { (byte) 'A' }));
	}

}
