package org.springframework.security.crypto.password;

import org.junit.Test;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;

import static org.assertj.core.api.Assertions.assertThat;

public class DigesterTests {

	@Test
	public void digestIsCorrectFor3Iterations() {
		Digester digester = new Digester("SHA-1", 3);
		byte[] result = digester.digest(Utf8.encode("text"));
		// echo -n text | openssl sha1 -binary | openssl sha1 -binary | openssl sha1
		assertThat(new String(Hex.encode(result))).isEqualTo("3cfa28da425eca5b894f0af2b158adf7001e000f");
	}

}
