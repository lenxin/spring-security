package org.springframework.security.crypto.codec;

import java.util.Arrays;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luke Taylor
 */
public class Utf8Tests {

	// SEC-1752
	@Test
	public void utf8EncodesAndDecodesCorrectly() throws Exception {
		byte[] bytes = Utf8.encode("6048b75ed560785c");
		assertThat(bytes).hasSize(16);
		assertThat(Arrays.equals("6048b75ed560785c".getBytes("UTF-8"), bytes)).isTrue();
		String decoded = Utf8.decode(bytes);
		assertThat(decoded).isEqualTo("6048b75ed560785c");
	}

}
